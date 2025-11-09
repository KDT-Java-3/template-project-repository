package com.sprata.sparta_ecommerce.dto;

import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private Long id;
    private Long user_id;
    private Long product_id;
    private String product_name;
    private int quantity;
    private String shipping_address;
    private OrderStatus status;
    private LocalDateTime order_date;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.user_id = order.getUserId();
        this.product_id = order.getProduct().getId();
        this.product_name = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.shipping_address = order.getShippingAddress();
        this.status = order.getStatus();
        this.order_date = order.getCreatedAt();
    }
}
