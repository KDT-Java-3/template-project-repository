package com.jaehyuk.week_01_project.domain.purchase.dto;

import com.jaehyuk.week_01_project.domain.purchase.entity.Purchase;
import com.jaehyuk.week_01_project.domain.purchase.entity.PurchaseProduct;
import com.jaehyuk.week_01_project.domain.purchase.enums.PurchaseStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 주문 조회 응답 DTO
 */
@Builder
public record PurchaseResponse(
        Long purchaseId,
        PurchaseStatus status,
        BigDecimal totalPrice,
        LocalDateTime createdAt,
        List<PurchaseProductResponse> products
) {
    /**
     * Purchase와 PurchaseProduct 목록을 PurchaseResponse로 변환
     */
    public static PurchaseResponse from(Purchase purchase, List<PurchaseProduct> purchaseProducts) {
        return PurchaseResponse.builder()
                .purchaseId(purchase.getId())
                .status(purchase.getStatus())
                .totalPrice(purchase.getTotalPrice())
                .createdAt(purchase.getCreatedAt())
                .products(purchaseProducts.stream()
                        .map(PurchaseProductResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
