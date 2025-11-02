package com.spartaecommerce.order.presentation.controller.dto.response;

import com.spartaecommerce.order.application.dto.OrderItemInfo;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long orderItemId,
    Long productId,
    String productName,
    BigDecimal price,
    Integer quantity,
    BigDecimal subtotal
) {
    public static OrderItemResponse from(OrderItemInfo itemInfo) {
        return new OrderItemResponse(
            itemInfo.orderItemId(),
            itemInfo.productId(),
            itemInfo.productName(),
            itemInfo.price(),
            itemInfo.quantity(),
            itemInfo.subtotal()
        );
    }
}