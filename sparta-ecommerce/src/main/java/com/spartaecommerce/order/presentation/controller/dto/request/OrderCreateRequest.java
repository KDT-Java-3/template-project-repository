package com.spartaecommerce.order.presentation.controller.dto.request;

import com.spartaecommerce.order.domain.command.OrderCreateCommand;
import jakarta.validation.constraints.*;

public record OrderCreateRequest(
    @NotNull
    Long userId,

    @NotNull
    Long productId,

    @NotNull
    @Positive
    @Min(value = 1, message = "최소 1개 이상 주문해야 합니다")
    @Max(value = 100, message = "한 번에 최대 100개까지만 주문 가능합니다")
    Integer quantity,

    @NotBlank
    String shippingAddress
) {
    public OrderCreateCommand toCommand() {
        return new OrderCreateCommand(
            userId,
            productId,
            quantity,
            shippingAddress
        );
    }
}
