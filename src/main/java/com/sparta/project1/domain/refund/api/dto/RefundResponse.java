package com.sparta.project1.domain.refund.api.dto;

import com.sparta.project1.domain.order.api.dto.OrderResponse;

import java.time.LocalDateTime;

public record RefundResponse(
        Long refundId,
        OrderResponse orders,
        String status,
        String reason,
        LocalDateTime createdAt
) {
}
