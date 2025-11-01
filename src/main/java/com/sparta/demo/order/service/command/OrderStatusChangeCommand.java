package com.sparta.demo.order.service.command;

import com.sparta.demo.order.domain.OrderStatus;

public record OrderStatusChangeCommand(
        Long orderId,
        OrderStatus newStatus
) {
}
