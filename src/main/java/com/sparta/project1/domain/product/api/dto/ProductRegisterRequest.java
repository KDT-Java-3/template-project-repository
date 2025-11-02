package com.sparta.project1.domain.product.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductRegisterRequest(
        @NotNull
        String name,
        @Min(1)
        Long price,
        String description,
        @Min(0)
        Integer stock,
        @NotNull
        Long categoryId
) {
}
