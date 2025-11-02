package com.example.stproject.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {
    @NotNull
    private Long id;

    private String name;

    private String description;

    @Min(0)
    private Long price;

    @Min(0)
    private Integer stock;

    private Long categoryId;
}
