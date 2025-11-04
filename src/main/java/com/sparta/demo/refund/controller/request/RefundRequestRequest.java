package com.sparta.demo.refund.controller.request;

import com.sparta.demo.refund.service.command.RefundRequestCommand;

public record RefundRequestRequest(
        Long userId,
        Long orderId,
        String reason
) {

    public RefundRequestCommand toCommand() {
        return new RefundRequestCommand(userId, orderId, reason);
    }
}
