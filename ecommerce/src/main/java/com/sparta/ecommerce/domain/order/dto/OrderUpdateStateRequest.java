package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateStateRequest {
    private Long id;
    private OrderStatus orderStatus;

}
