package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.enums.RefundStatus;
import io.depark.commerceservice.service.dto.RefundDetailResult;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RefundDetailResponse {
    private Long refundId;
    private Long purchaseId;
    private RefundStatus status;
    private String reason;
    private List<PurchaseProductInfo> purchaseProducts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RefundDetailResponse of(RefundDetailResult result) {
        return RefundDetailResponse.builder()
                .refundId(result.getRefundId())
                .purchaseId(result.getPurchaseId())
                .status(result.getStatus())
                .reason(result.getReason())
                .purchaseProducts(result.getPurchaseProducts().stream().map(PurchaseProductInfo::of).toList())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }

    @Getter
    @Builder
    public static class PurchaseProductInfo {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subTotal;

        public static PurchaseProductInfo of(RefundDetailResult.PurchaseProductInfo info) {
            return PurchaseProductInfo.builder()
                    .productId(info.getProductId())
                    .productName(info.getProductName())
                    .quantity(info.getQuantity())
                    .price(info.getPrice())
                    .subTotal(info.getSubTotal())
                    .build();
        }
    }
}
