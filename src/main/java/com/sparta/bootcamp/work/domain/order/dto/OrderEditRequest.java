package com.sparta.bootcamp.work.domain.order.dto;


import com.sparta.bootcamp.work.common.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderEditRequest {

    @NotNull
    private Long userId;

    private Long orderId;

    @NotNull
    private OrderStatus orderStatus;
}
