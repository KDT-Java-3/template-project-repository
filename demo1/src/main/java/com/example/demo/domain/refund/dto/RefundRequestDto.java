package com.example.demo.domain.refund.dto;

import com.example.demo.domain.Status;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefundRequestDto {

    @NotNull(message = "환불 이유는 필수입니다.")
    String reason;

    @NotNull(message = "유저 아이디는 필수입니다.")
    Long userId;

    @NotNull(message = "주문 아이디는 필수입니다.")
    Long orderId;

}
