package com.sparta.heesue.dto.response;

import com.sparta.heesue.entity.OrderItem;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItemResponseDto {
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public OrderItemResponseDto(OrderItem item) {
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.totalPrice = item.getTotalPrice();
    }
}
