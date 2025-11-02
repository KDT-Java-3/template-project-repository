package com.example.demo.domain.purchase;

import com.example.demo.domain.product.Product;
import com.example.demo.domain.product.ProductRepository;
import com.example.demo.domain.purchaseproduct.PurchaseProduct;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor // 의존성 주입
public class PurchaseService {
    // ===== 의존성 주입 =====
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // ============================================
    // -- 주문 생성
    // 비즈니스 로직:
    // 1. User 조회
    // 2. Purchase Entity 생성
    // 3. 각 상품 처리
    //    3-1. Product 조회
    //    3-2. 재고 확인
    //    3-3. 재고 감소
    //    3-4. PurchaseProduct 생성
    //    3-5. Purchase에 추가
    //    3-6. 총액 계산
    // 4. 총액 설정
    // 5. Purchase 엔티티 DB 저장
    // 6. Entity → DTO 변환 후 반환
    // ============================================
    @Transactional  // DB 작업을 하나로 묶어서, 모두 성공하거나 모두 취소하게 만드는 것
    public PurchaseDto.Response createPurchase(PurchaseDto.Request request) {
        // --- User Entity ---
        // 1. User 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // --- Purchase Entity ---
        // 2. Purchase Entity 생성 (빈 상태)
        Purchase purchase = Purchase.builder()
                .userId(user)  // 1. User 조회
                .shippingAddress(request.getShippingAddress())
                .status("pending")
                .totalPrice(BigDecimal.ZERO)  // 나중에 계산
                .build();
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 3. 각 상품 처리 (Request의 OrderItem 사용)
        for (PurchaseDto.Request.OrderItem orderItem : request.getProducts()) {

            // --- Product Entity ---
            // 3-1. Product 조회
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            // 3-2. 재고 확인
            if (product.getStockQuantity() < orderItem.getQuantity()) {
                throw new IllegalArgumentException(
                        "재고 부족: " + product.getName() +
                                " (재고: " + product.getStockQuantity() +
                                ", 요청: " + orderItem.getQuantity() + ")"
                );
            }

            // 3-3. 재고 감소
            product.setStockQuantity(
                    product.getStockQuantity() - orderItem.getQuantity()
            );

            // --- PurchaseProduct Entity ---
            // 3-4. PurchaseProduct 생성
            PurchaseProduct purchaseProduct = PurchaseProduct.builder()
                    .purchase(purchase)
                    .product(product)
                    .quantity(orderItem.getQuantity())  // ← PurchaseDto.Request에서 가져옴
                    .price(product.getPrice())
                    .build();

            // 3-5. Purchase에 추가
            purchase.getPurchaseProducts().add(purchaseProduct);

            // 3-6. 총액 계산
            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalPrice = totalPrice.add(subtotal);
        }

        // 4. 총액 설정
        purchase.setTotalPrice(totalPrice);

        // 5. 저장 (cascade로 PurchaseProduct도 함께 저장됨)
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // 6. Response 반환
        return PurchaseDto.Response.from(savedPurchase);
    }
}
