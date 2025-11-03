package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.Order;
import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private List<OrderProductDto> products;
    private LocalDateTime dateTime;

    public static OrderResponse fromEntity(Order order) {
        List<OrderProductDto> products = order.getOrderProductList().stream().map(OrderProductDto::fromEntity).toList();
        return builder().status(order.getStatus()).dateTime(order.getOrderDate()).products(products).build();
    }
}
