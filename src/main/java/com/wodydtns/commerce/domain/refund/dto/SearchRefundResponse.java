package com.wodydtns.commerce.domain.refund.dto;

import java.time.LocalDate;

import com.wodydtns.commerce.global.enums.RefundStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchRefundResponse {

    private String reason;
    private RefundStatus refundStatus;
    private LocalDate createdAt;

}
