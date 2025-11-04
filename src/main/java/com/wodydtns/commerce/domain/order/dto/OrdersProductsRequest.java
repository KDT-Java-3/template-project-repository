package com.wodydtns.commerce.domain.order.dto;

import com.wodydtns.commerce.global.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersProductsRequest {
    private OrderStatus orderStatus;
}
