package com.sparta.proejct1101.domain.order.dto.response;

import com.sparta.proejct1101.domain.order.entity.Order;
import com.sparta.proejct1101.domain.order.entity.OrderStatus;
import com.sparta.proejct1101.domain.product.dto.response.ProductRes;
import com.sparta.proejct1101.domain.user.dto.response.UserRes;

import java.time.LocalDateTime;

public record OrderRes(
        Long orderId,
        UserRes user,
        ProductRes product,
        Integer quantity,
        String shippingAddress,
        OrderStatus status,
        LocalDateTime orderDate
) {
    public static OrderRes from(Order order) {
        return new OrderRes(
                order.getId(),
                order.getUser() != null ? UserRes.from(order.getUser()) : null,
                order.getProduct() != null ? ProductRes.from(order.getProduct()) : null,
                order.getQuantity(),
                order.getShippingAddress(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}