package io.depark.commerceservice.service.dto;

import io.depark.commerceservice.entity.enums.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class RefundDetailResult {
    private Long refundId;
    private Long purchaseId;
    private RefundStatus status;
    private String reason;
    private List<PurchaseProductInfo> purchaseProducts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @AllArgsConstructor
    public static class PurchaseProductInfo {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subTotal;
    }
}
