package com.sparta.demo.refund.service.command;

public record RefundProcessCommand(
        Long refundId,
        boolean approved
) {
}
