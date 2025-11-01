package com.sparta.bootcamp.work.domain.order.dto;


import com.sparta.bootcamp.work.common.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderEditRequest {

    @NotBlank
    private Long userId;

    private Long orderId;

    @NotBlank
    private OrderStatus orderStatus;
}
