package com.sparta.ecommerce.domain.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderCreateRequest {
    private String name;
    private Long userId;
    private List<OrderCreateProductDto> products;
    private String shippingAddress;
}
