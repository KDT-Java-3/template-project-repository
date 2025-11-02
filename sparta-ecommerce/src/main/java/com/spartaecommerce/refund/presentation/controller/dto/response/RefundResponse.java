package com.spartaecommerce.refund.presentation.controller.dto.response;

import com.spartaecommerce.refund.application.dto.RefundInfo;
import com.spartaecommerce.refund.domain.entity.RefundStatus;

import java.time.LocalDateTime;

public record RefundResponse(
    Long refundId,
    Long userId,
    Long orderId,
    String reason,
    RefundStatus status,
    LocalDateTime createdAt
) {
    public static RefundResponse from(RefundInfo refundInfo) {
        return new RefundResponse(
            refundInfo.refundId(),
            refundInfo.userId(),
            refundInfo.orderId(),
            refundInfo.reason(),
            refundInfo.status(),
            refundInfo.createdAt()
        );
    }
}