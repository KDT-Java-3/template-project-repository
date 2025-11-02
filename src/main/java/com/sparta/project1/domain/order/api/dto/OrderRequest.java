package com.sparta.project1.domain.order.api.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(@NotNull Long userId,
                           List<ProductOrderRequest> products,
                           @NotNull String shippingAddress) {
}
