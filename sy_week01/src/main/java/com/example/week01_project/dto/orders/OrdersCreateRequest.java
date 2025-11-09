package com.example.week01_project.dto.orders;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrdersCreateRequest(
        @NotNull Long userId,
        @NotNull Long productId,
        @Min(1) int quantity,
        @NotBlank String shippingAddress
) {}
