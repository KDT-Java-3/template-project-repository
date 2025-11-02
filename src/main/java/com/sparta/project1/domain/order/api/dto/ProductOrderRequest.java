package com.sparta.project1.domain.order.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductOrderRequest(@NotNull Long productId, @Min(1) Integer quantity) {
}
