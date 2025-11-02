package com.sparta.sangmin.controller.dto;

public record RefundRequest(
        Long userId,
        Long orderId,
        String reason
) {
}
