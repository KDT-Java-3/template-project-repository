package com.spartaecommerce.order.application.dto;

import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderInfo(
    Long orderId,
    Long userId,
    OrderStatus status,
    BigDecimal totalAmount,
    String shippingAddress,
    List<OrderItemInfo> orderItems,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static OrderInfo from(Order order) {
        List<OrderItemInfo> orderItemInfos = order.getOrderItems().stream()
            .map(OrderItemInfo::from)
            .toList();

        return new OrderInfo(
            order.getOrderId(),
            order.getUserId(),
            order.getStatus(),
            order.calculateTotalAmount(),
            order.getShippingAddress(),
            orderItemInfos,
            order.getCreatedAt(),
            order.getUpdatedAt()
        );
    }
}
