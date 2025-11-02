package com.wodydtns.commerce.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UpdateOrderRequest {
    private List<Long> productIds;
    private Integer quantity;
    private String shippingAddress;
    private String orderStatus;
}
