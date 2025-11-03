package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.Order;
import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderUpsertDto {
    private Long id;
    private OrderStatus status;
    private String ShippingAddress;

    public static OrderUpsertDto fromEntity(Order order) {
        return builder().id(order.getId()).status(order.getStatus()).ShippingAddress(order.getShippingAddress()).build();
    }
}

