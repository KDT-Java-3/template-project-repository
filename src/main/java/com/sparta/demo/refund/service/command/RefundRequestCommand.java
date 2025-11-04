package com.sparta.demo.refund.service.command;

public record RefundRequestCommand(
        Long userId,
        Long orderId,
        String reason
) {
}
