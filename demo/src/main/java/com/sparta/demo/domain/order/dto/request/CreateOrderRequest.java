package com.sparta.demo.domain.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderRequest {
    @NotNull @Min(1)
    private Long userId;
    @NotNull @Min(1)
    private Long productId;
    @NotNull @Min(1)
    private Integer quantity;
    @NotNull
    private String shippingAddress;
}
