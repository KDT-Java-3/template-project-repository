package com.sparta.ecommerce.purchase.application;

import static com.sparta.ecommerce.purchase.application.dto.PurchaseDto.*;

import com.sparta.ecommerce.purchase.domain.Purchase;
import com.sparta.ecommerce.purchase.domain.PurchaseStatus;
import com.sparta.ecommerce.purchase.infrastructure.PurchaseJpaRepository;
import com.sparta.ecommerce.product.domain.Product;
import com.sparta.ecommerce.product.infrastructure.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseJpaRepository purchaseJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    @Transactional
    public PurchaseResponse createPurchase(PurchaseCreateRequest createRequest) {
        //TODO userId 검증 필요

        // 제품 번호 검증 필요.
        Product product = productJpaRepository.findByIdForUpdate(createRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        // 제품에 재고가 있는지 확인이 필요함.
        product.decreaseStock(createRequest.getQuantity());
        Purchase purchase = createRequest.toEntity(product);
        purchase = purchaseJpaRepository.save(purchase);

        return PurchaseResponse.fromEntity(purchase);
    }

    @Transactional
    public PurchaseResponse changeStatus(Long id, PurchaseStatus status) {
        Purchase purchase = purchaseJpaRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
        Product product = productJpaRepository.findByIdForUpdate(purchase.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        purchase.changeStatus(status, product);
        return PurchaseResponse.fromEntity(purchase);
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponse> getUserPurchases(Long userId) {
        return purchaseJpaRepository.findAllByUserId(userId)
                .stream().map(PurchaseResponse::fromEntity).toList();
    }
}
