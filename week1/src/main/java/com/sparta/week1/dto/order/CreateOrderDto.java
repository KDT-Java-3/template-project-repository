package com.sparta.week1.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderDto {
    private Integer quantity;
    private Long userId;
    private Long productId;
    private String status;
}
