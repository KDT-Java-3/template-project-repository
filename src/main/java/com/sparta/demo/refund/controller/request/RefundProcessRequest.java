package com.sparta.demo.refund.controller.request;

import com.sparta.demo.refund.service.command.RefundProcessCommand;

public record RefundProcessRequest(
        boolean approved
) {

    public RefundProcessCommand toCommand(Long refundId) {
        return new RefundProcessCommand(refundId, approved);
    }
}
