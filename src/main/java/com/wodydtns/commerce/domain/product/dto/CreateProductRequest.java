package com.wodydtns.commerce.domain.product.dto;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;

@Getter

public class CreateProductRequest {

    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    private int stock;
    @NotNull
    private Long categoryId;
    private String description;
}
