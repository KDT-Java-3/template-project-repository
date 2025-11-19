package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.Refund;
import io.depark.commerceservice.entity.enums.RefundStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RefundResponse {
    private Long refundId;
    private Long purchaseId;
    private RefundStatus status;
    private String reason;
    private LocalDateTime createdAt;

    public static RefundResponse fromEntity(Refund refund) {
        return RefundResponse.builder()
                .refundId(refund.getId())
                .purchaseId(refund.getPurchase().getId())
                .status(refund.getStatus())
                .reason(refund.getReason())
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
