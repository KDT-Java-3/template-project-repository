package com.wodydtns.commerce.domain.refund.entity;

import com.wodydtns.commerce.domain.member.entity.Member;
import com.wodydtns.commerce.domain.order.entity.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundRequest {

    @NotNull
    private Member userId;

    @NotNull
    private Order orderId;

    @NotBlank
    private String reason;

}
