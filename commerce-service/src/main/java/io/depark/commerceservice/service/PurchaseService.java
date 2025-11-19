package io.depark.commerceservice.service;

import io.depark.commerceservice.common.ServiceException;
import io.depark.commerceservice.common.ServiceExceptionCode;
import io.depark.commerceservice.controller.dto.PurchaseDetailResponse;
import io.depark.commerceservice.controller.dto.PurchaseRequest;
import io.depark.commerceservice.controller.dto.PurchaseResponse;
import io.depark.commerceservice.controller.dto.PurchaseSearchCondition;
import io.depark.commerceservice.entity.Product;
import io.depark.commerceservice.entity.Purchase;
import io.depark.commerceservice.entity.PurchaseProduct;
import io.depark.commerceservice.entity.User;
import io.depark.commerceservice.entity.enums.PurchaseStatus;
import io.depark.commerceservice.repository.ProductJpaRepository;
import io.depark.commerceservice.repository.PurchaseJpaRepository;
import io.depark.commerceservice.repository.PurchaseQueryRepository;
import io.depark.commerceservice.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseJpaRepository purchaseJpaRepository;
    private final PurchaseQueryRepository purchaseQueryRepository;
    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Transactional(readOnly = true)
    public PurchaseDetailResponse getPurchase(Long id) {
        return PurchaseDetailResponse.fromEntity(
                purchaseJpaRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PURCHASE))
        );
    }

    @Transactional(readOnly = true)
    public Page<PurchaseResponse> searchPurchases(PurchaseSearchCondition condition, Pageable pageable) {
        Page<Purchase> purchases = purchaseQueryRepository.searchProducts(condition, pageable);
        return purchases.map(PurchaseResponse::fromEntity);
    }

    @Transactional
    public PurchaseResponse create(PurchaseRequest request) {
        // 사용자 조회
        User user = userJpaRepository.findById(request.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_USER));

        // 상품 아이디 리스트 추출
        List<Long> productIds = request.getProducts().stream()
                .map(PurchaseRequest.PurchaseProduct::getProductId)
                .toList();

        // Product 엔티티 맵 조회
        Map<Long, Product> productMap = productJpaRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // 재고 차감 및 PurchaseProduct 엔티티 변환 (아직 Purchase 부모 맵핑 X)
        List<PurchaseProduct> purchaseProducts = request.getProducts().stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());
                    if (product == null) {
                        throw new ServiceException(ServiceExceptionCode.NOT_FOUND_PRODUCT);
                    }
                    product.decreaseStock(item.getQuantity());

                    return PurchaseProduct.builder()
                            .product(product)
                            .quantity(item.getQuantity())
                            .price(product.getPrice())
                            .build();
                })
                .toList();

        // 총액 계산
        BigDecimal totalPrice = purchaseProducts.stream()
                .map(PurchaseProduct::calculateToTalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Purchase - PurchaseProduct 연관 관계 설정
        Purchase purchase = Purchase.builder()
                .user(user)
                .totalPrice(totalPrice)
                .status(PurchaseStatus.PENDING)
                .shippingAddress(request.getShippingAddress())
                .build();

        purchaseProducts.forEach(purchase::addPurchaseProduct);

        return PurchaseResponse.fromEntity(
                purchaseJpaRepository.save(purchase)
        );
    }

    @Transactional
    public void cancel(Long id) {
        Purchase purchase = purchaseJpaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_PURCHASE));

        purchase.cancel();
    }
}
