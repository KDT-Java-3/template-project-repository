package com.sparta.sangmin.controller.dto;

import com.sparta.sangmin.domain.Order;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long userId,
        Long productId,
        String productName,
        Integer quantity,
        Integer totalPrice,
        String shippingAddress,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getProduct().getId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getShippingAddress(),
                order.getStatus().name(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
