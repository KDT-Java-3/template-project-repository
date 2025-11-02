package com.sparta.commerce.domain.order.dto;

import com.sparta.commerce.entity.Order;
import com.sparta.commerce.entity.OrderProduct;
import com.sparta.commerce.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(
        Long id,
        Long userId,
        List<OrderProductDto> orderItems,
        String shippingAddress,
        BigDecimal totalPrice,
        OrderStatus status
) {
    public static OrderDto of(
            Order order,
            List<OrderProduct> orderProducts
    ) {
        List<OrderProductDto> orderItemDtos = orderProducts.stream()
                .map(OrderProductDto::of)
                .toList();

        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                orderItemDtos,
                order.getShippingAddress(),
                order.getTotalPrice(),
                order.getStatus()
        );
    }
}
