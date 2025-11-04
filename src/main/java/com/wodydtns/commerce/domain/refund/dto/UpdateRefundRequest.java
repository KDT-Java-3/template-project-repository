package com.wodydtns.commerce.domain.refund.dto;

import com.wodydtns.commerce.global.enums.RefundStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateRefundRequest {

    private long id;

    private RefundStatus refundStatus;

    private long productId;
}
