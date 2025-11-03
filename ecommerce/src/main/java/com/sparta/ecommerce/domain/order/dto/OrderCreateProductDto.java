package com.sparta.ecommerce.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateProductDto {
    private Long productId;
    private int quantity;
}
