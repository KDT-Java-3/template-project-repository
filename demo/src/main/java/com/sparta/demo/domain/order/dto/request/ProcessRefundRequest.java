package com.sparta.demo.domain.order.dto.request;

import com.sparta.demo.domain.order.entity.Refund;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProcessRefundRequest {
    @NotNull
    private Long refundId;

    @NotNull
    private Refund.RefundStatus action; // APPROVED or REJECTED
}

