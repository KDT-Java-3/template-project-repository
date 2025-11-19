package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.Purchase;
import io.depark.commerceservice.entity.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class PurchaseResponse {
    private Long purchaseId;
    private Long userId;
    private BigDecimal totalPrice;
    private PurchaseStatus status;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PurchaseResponse fromEntity(Purchase purchase) {
        return PurchaseResponse.builder()
                .purchaseId(purchase.getId())
                .userId(purchase.getUser().getId())
                .totalPrice(purchase.getTotalPrice())
                .status(purchase.getStatus())
                .shippingAddress(purchase.getShippingAddress())
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt())
                .build();
    }
}
