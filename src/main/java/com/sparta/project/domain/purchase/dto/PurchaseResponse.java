package com.sparta.project.domain.purchase.dto;

import com.sparta.project.common.enums.PurchaseStatus;
import com.sparta.project.domain.purchase.entity.Purchase;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PurchaseResponse {
    private Long id;
    private Long userId;
    private String username;
    private BigDecimal totalPrice;
    private PurchaseStatus status;
    private String statusDescription;
    private List<PurchaseItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Builder
    public static class PurchaseItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal; // quantity * price
    }

    public static PurchaseResponse from(Purchase purchase) {
        List<PurchaseItemResponse> items = purchase.getPurchaseProducts().stream()
                .map(pp -> PurchaseItemResponse.builder()
                        .productId(pp.getProduct().getId())
                        .productName(pp.getProduct().getName())
                        .quantity(pp.getQuantity())
                        .price(pp.getPrice())
                        .subtotal(pp.getPrice().multiply(BigDecimal.valueOf(pp.getQuantity())))
                        .build())
                .collect(Collectors.toList());

        return PurchaseResponse.builder()
                .id(purchase.getId())
                .userId(purchase.getUser().getId())
                .username(purchase.getUser().getUsername())
                .totalPrice(purchase.getTotalPrice())
                .status(purchase.getStatus())
                .statusDescription(purchase.getStatus().getDescription())
                .items(items)
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt())
                .build();
    }

    public static List<PurchaseResponse> fromList(List<Purchase> purchases) {
        return purchases.stream()
                .map(PurchaseResponse::from)
                .collect(Collectors.toList());
    }
}