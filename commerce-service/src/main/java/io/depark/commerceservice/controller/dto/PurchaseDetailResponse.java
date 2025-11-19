package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.Purchase;
import io.depark.commerceservice.entity.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PurchaseDetailResponse {
    private Long purchaseId;
    private Long userId;
    private BigDecimal totalPrice;
    private PurchaseStatus status;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PurchaseProduct> purchaseProducts;

    @Getter
    @Builder
    public static class PurchaseProduct {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subTotal;
    }

    public static PurchaseDetailResponse fromEntity(Purchase purchase) {
        return PurchaseDetailResponse.builder()
                .purchaseId(purchase.getId())
                .userId(purchase.getUser().getId())
                .totalPrice(purchase.getTotalPrice())
                .status(purchase.getStatus())
                .shippingAddress(purchase.getShippingAddress())
                .createdAt(purchase.getCreatedAt())
                .updatedAt(purchase.getUpdatedAt())
                .purchaseProducts(purchase.getPurchaseProducts().stream()
                        .map(p -> PurchaseProduct.builder()
                                .productId(p.getProduct().getId())
                                .productName(p.getProduct().getName())
                                .quantity(p.getQuantity())
                                .price(p.getPrice())
                                .subTotal(p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                                .build()
                        )
                        .toList()
                )
                .build();
    }
}
