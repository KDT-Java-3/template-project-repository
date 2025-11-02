package com.wodydtns.commerce.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateOrderDto {
    private Long memberId;
    private List<Long> productIds;
    private int quantity;
    private String shippingAddress;
}
