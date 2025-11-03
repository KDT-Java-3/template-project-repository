package com.sparta.proejct1101.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record OrderReq(
        @NotNull
        Long userId,

        @NotNull
        Long productId,

        @NotNull
        Integer quantity,

        @NotNull
        String shippingAddress
) {
}