package com.spartaecommerce.product.presentation.controller.dto;

import com.spartaecommerce.common.domain.Money;
import com.spartaecommerce.product.application.dto.ProductRegisterCommand;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRegisterRequest(
    @NotBlank
    @Size(min = 1, max = 100)
    String name,

    @NotNull
    @DecimalMin(value = "0.0")
    BigDecimal price,

    @NotNull
    Integer stock,

    @NotNull
    Long categoryId
) {

    public ProductRegisterCommand toCommand() {
        return new ProductRegisterCommand(
            name, Money.of(price), stock, categoryId
        );
    }
}
