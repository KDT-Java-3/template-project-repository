package com.sparta.project.domain.purchase.service;


import com.sparta.project.common.enums.PurchaseStatus;
import com.sparta.project.domain.product.entity.Product;
import com.sparta.project.domain.product.repository.ProductRepository;
import com.sparta.project.domain.purchase.dto.PurchaseCreateRequest;
import com.sparta.project.domain.purchase.dto.PurchaseItemRequest;
import com.sparta.project.domain.purchase.dto.PurchaseResponse;
import com.sparta.project.domain.purchase.dto.PurchaseStatusUpdateRequest;
import com.sparta.project.domain.purchase.entity.Purchase;
import com.sparta.project.domain.purchase.entity.PurchaseProduct;
import com.sparta.project.domain.purchase.repository.PurchaseProductRepository;
import com.sparta.project.domain.purchase.repository.PurchaseRepository;
import com.sparta.project.domain.user.entity.User;
import com.sparta.project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public PurchaseResponse createPurchase(PurchaseCreateRequest request) {
        // 사용자 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + request.getUserId()));

        // 총 금액 계산 및 재고 확인
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (PurchaseItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + item.getProductId()));

            // 재고 확인
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalStateException(
                        String.format("상품 '%s'의 재고가 부족합니다. 요청 수량: %d, 현재 재고: %d",
                                product.getName(), item.getQuantity(), product.getStock())
                );
            }

            // 총 금액 계산
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }

        // 주문 생성
        Purchase purchase = Purchase.builder()
                .user(user)
                .totalPrice(totalPrice)
                .status(PurchaseStatus.PENDING)
                .build();

        Purchase savedPurchase = purchaseRepository.save(purchase);

        // 주문 상품 생성 및 재고 감소
        for (PurchaseItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + item.getProductId()));

            // 주문 상품 생성
            PurchaseProduct purchaseProduct = PurchaseProduct.builder()
                    .purchase(savedPurchase)
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(product.getPrice()) // 주문 당시 가격 저장
                    .build();

            purchaseProductRepository.save(purchaseProduct);
            savedPurchase.addPurchaseProduct(purchaseProduct);

            // 재고 감소
            product.decreaseStock(item.getQuantity());
        }

        return PurchaseResponse.from(savedPurchase);
    }

    /**
     * 특정 사용자의 모든 주문 조회
     */
    public List<PurchaseResponse> getUserPurchases(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

        List<Purchase> purchases = purchaseRepository.findByUserOrderByCreatedAtDesc(user);
        return PurchaseResponse.fromList(purchases);
    }

    /**
     * 주문 상태 변경
     */
    @Transactional
    public PurchaseResponse updatePurchaseStatus(Long purchaseId, PurchaseStatusUpdateRequest request) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + purchaseId));

        // 상태 변경
        purchase.updateStatus(request.getStatus());

        if (request.getStatus() == PurchaseStatus.CANCELED) {
            restoreStock(purchase); // 재고 복구
        }

        return PurchaseResponse.from(purchase);
    }

    /**
     * 주문 취소
     */
    @Transactional
    public PurchaseResponse cancelPurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + purchaseId));

        // 주문 취소
        purchase.cancel();

        restoreStock(purchase); // 재고 복구

        return PurchaseResponse.from(purchase);
    }

    private void restoreStock(Purchase purchase) {
        for (PurchaseProduct purchaseProduct : purchase.getPurchaseProducts()) {
            Product product = purchaseProduct.getProduct();
            product.increaseStock(purchaseProduct.getQuantity());
        }
    }
}