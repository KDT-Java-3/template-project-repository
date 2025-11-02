package com.sparta.heesue.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private Long userId;
    private Long productId;
    private int quantity;
    private String shippingAddress;
}
