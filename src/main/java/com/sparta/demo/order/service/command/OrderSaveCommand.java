package com.sparta.demo.order.service.command;

public record OrderSaveCommand(
        Long userId,
        Long productId,
        int quantity,
        String shippingAddress
) {
}
