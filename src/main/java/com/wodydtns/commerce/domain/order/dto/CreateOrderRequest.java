package com.wodydtns.commerce.domain.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderRequest {
    private Long userId;
    private List<Integer> products;
    private int quantity;
    private String shippingAddress;
}
