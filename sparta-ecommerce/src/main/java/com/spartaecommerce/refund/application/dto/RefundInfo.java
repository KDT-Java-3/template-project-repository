package com.spartaecommerce.refund.application.dto;

import com.spartaecommerce.refund.domain.entity.Refund;
import com.spartaecommerce.refund.domain.entity.RefundStatus;

import java.time.LocalDateTime;

public record RefundInfo(
    Long refundId,
    Long userId,
    Long orderId,
    String reason,
    RefundStatus status,
    LocalDateTime createdAt
) {
    public static RefundInfo from(Refund refund) {
        return new RefundInfo(
            refund.getRefundId(),
            refund.getUserId(),
            refund.getOrderId(),
            refund.getReason(),
            refund.getStatus(),
            refund.getCreatedAt()
        );
    }
}