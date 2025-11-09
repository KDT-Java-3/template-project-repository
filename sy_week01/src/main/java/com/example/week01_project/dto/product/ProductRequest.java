package com.example.week01_project.dto.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin("0.0") @Digits(integer=12, fraction=2) BigDecimal price,
        @Min(0) int stock,
        @NotNull Long categoryId
) {}
