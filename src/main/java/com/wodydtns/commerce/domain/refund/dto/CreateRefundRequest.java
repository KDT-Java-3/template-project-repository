package com.wodydtns.commerce.domain.refund.dto;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Getter
public class CreateRefundRequest {

    @NotNull
    private String userId;
    @NotNull
    private Long orderId;
    @NotNull
    private String reason;
}
