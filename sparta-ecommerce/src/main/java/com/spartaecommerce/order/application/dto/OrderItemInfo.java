package com.spartaecommerce.order.application.dto;

import com.spartaecommerce.order.domain.entity.OrderItem;

import java.math.BigDecimal;

public record OrderItemInfo(
    Long orderItemId,
    Long productId,
    String productName,
    BigDecimal price,
    Integer quantity,
    BigDecimal subtotal
) {
    public static OrderItemInfo from(OrderItem item) {
        return new OrderItemInfo(
            item.getOrderItemId(),
            item.getProductId(),
            item.getProductName(),
            item.getProductPrice().amount(),
            item.getQuantity(),
            item.getSubtotal().amount()
        );
    }

}
