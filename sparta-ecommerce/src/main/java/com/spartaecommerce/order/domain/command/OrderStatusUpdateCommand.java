package com.spartaecommerce.order.domain.command;

import com.spartaecommerce.order.domain.entity.OrderStatus;

public record OrderStatusUpdateCommand(
    Long orderId,
    OrderStatus orderStatus
) {
}
