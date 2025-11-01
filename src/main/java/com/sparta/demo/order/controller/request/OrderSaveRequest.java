package com.sparta.demo.order.controller.request;

import com.sparta.demo.order.service.command.OrderSaveCommand;

public record OrderSaveRequest(
        Long userId,
        Long productId,
        int quantity,
        String shippingAddress
) {

    public OrderSaveCommand toCommand() {
        return new OrderSaveCommand(userId, productId, quantity, shippingAddress);
    }
}
