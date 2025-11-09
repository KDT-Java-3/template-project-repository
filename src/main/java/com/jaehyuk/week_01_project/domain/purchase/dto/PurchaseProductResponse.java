package com.jaehyuk.week_01_project.domain.purchase.dto;

import com.jaehyuk.week_01_project.domain.purchase.entity.PurchaseProduct;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * 주문 상품 응답 DTO
 */
@Builder
public record PurchaseProductResponse(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {
    /**
     * PurchaseProduct 엔티티를 PurchaseProductResponse로 변환
     */
    public static PurchaseProductResponse from(PurchaseProduct purchaseProduct) {
        return PurchaseProductResponse.builder()
                .productId(purchaseProduct.getProduct().getId())
                .productName(purchaseProduct.getProduct().getName())
                .quantity(purchaseProduct.getQuantity())
                .price(purchaseProduct.getPrice())
                .build();
    }
}
