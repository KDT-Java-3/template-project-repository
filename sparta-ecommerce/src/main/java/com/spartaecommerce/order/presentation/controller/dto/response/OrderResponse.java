package com.spartaecommerce.order.presentation.controller.dto.response;

import com.spartaecommerce.order.application.dto.OrderInfo;
import com.spartaecommerce.order.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Long orderId,
    Long userId,
    OrderStatus status,
    BigDecimal totalAmount,
    String shippingAddress,
    List<OrderItemResponse> orderItems,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static OrderResponse from(OrderInfo orderInfo) {
        List<OrderItemResponse> items = orderInfo.orderItems().stream()
            .map(OrderItemResponse::from)
            .toList();

        return new OrderResponse(
            orderInfo.orderId(),
            orderInfo.userId(),
            orderInfo.status(),
            orderInfo.totalAmount(),
            orderInfo.shippingAddress(),
            items,
            orderInfo.createdAt(),
            orderInfo.updatedAt()
        );
    }

}
