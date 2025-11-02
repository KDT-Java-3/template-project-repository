package com.sparta.sangmin.controller.dto;

import com.sparta.sangmin.domain.Refund;

import java.time.LocalDateTime;

public record RefundResponse(
        Long id,
        Long userId,
        Long orderId,
        String reason,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static RefundResponse from(Refund refund) {
        return new RefundResponse(
                refund.getId(),
                refund.getUser().getId(),
                refund.getOrder().getId(),
                refund.getReason(),
                refund.getStatus().name(),
                refund.getCreatedAt(),
                refund.getUpdatedAt()
        );
    }
}
