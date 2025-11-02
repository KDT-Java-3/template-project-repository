package com.sparta.sangmin.controller.dto;

public record OrderRequest(
        Long userId,
        Long productId,
        Integer quantity,
        String shippingAddress
) {
}
