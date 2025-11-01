package com.spartaecommerce.product.domain.query;

import com.spartaecommerce.common.domain.Money;

public record ProductSearchQuery(
    Long categoryId,
    Money minPrice,
    Money maxPrice,
    String keyword
) {
}
