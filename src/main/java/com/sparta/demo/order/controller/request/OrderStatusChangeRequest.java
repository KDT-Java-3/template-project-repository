package com.sparta.demo.order.controller.request;

import com.sparta.demo.order.domain.OrderStatus;
import com.sparta.demo.order.service.command.OrderStatusChangeCommand;

public record OrderStatusChangeRequest(
        OrderStatus newStatus
) {

    public OrderStatusChangeCommand toCommand(Long orderId) {
        return new OrderStatusChangeCommand(orderId, newStatus);
    }
}
