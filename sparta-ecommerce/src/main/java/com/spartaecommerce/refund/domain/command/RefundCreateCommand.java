package com.spartaecommerce.refund.domain.command;

public record RefundCreateCommand(
    Long userId,
    Long orderId,
    String reason
) {
}