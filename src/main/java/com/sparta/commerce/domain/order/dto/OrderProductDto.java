package com.sparta.commerce.domain.order.dto;

import com.sparta.commerce.entity.OrderProduct;

import java.math.BigDecimal;

public record OrderProductDto(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {
    public static OrderProductDto of(OrderProduct orderProduct) {
        return new OrderProductDto(
                orderProduct.getId(),
                orderProduct.getProduct().getId(),
                orderProduct.getProduct().getName(),
                orderProduct.getQuantity(),
                orderProduct.getPrice()
        );
    }
}
