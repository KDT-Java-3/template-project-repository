package com.sparta.demo.domain.order.dto.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateProductRequest {
    @NotNull @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer stock;
    @Nullable
    private String description;
    @NotNull
    private Long categoryId;
}
