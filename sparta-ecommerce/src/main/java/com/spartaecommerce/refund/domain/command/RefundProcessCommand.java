package com.spartaecommerce.refund.domain.command;

import com.spartaecommerce.refund.domain.entity.RefundStatus;

public record RefundProcessCommand(
    Long refundId,
    RefundStatus status
) {
}