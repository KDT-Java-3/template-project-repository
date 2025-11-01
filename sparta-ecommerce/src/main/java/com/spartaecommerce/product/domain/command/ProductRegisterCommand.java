package com.spartaecommerce.product.domain.command;

import com.spartaecommerce.common.domain.Money;

public record ProductRegisterCommand(
    String name,
    Money price,
    Integer stock,
    Long categoryId
) {
}
