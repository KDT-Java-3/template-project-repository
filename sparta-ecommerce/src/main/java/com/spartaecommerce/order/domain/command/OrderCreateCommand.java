package com.spartaecommerce.order.domain.command;

public record OrderCreateCommand(
    Long userId,
    Long productId,
    Integer quantity,
    String shippingAddress
) {
}
