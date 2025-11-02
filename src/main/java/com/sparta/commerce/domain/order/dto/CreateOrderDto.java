package com.sparta.commerce.domain.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderDto(
        @NotNull(message = "유저 정보는 필수입니다.")
        Long userId,

        @NotEmpty(message = "주문 상품은 최소 1개 이상이어야 합니다.")
        @Valid
        List<OrderItemDto> orderItems,

        @NotBlank(message = "배송지 주소는 필수입니다.")
        String shippingAddress
) {
}
