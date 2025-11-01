package com.spartaecommerce.product.presentation.controller.dto.request;

import com.spartaecommerce.common.domain.Money;
import com.spartaecommerce.product.domain.query.ProductSearchQuery;

import java.math.BigDecimal;

public record ProductSearchRequest(
    Long categoryId,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    String keyword
) {

    public ProductSearchQuery toQuery() {
        return new ProductSearchQuery(
            categoryId,
            Money.from(minPrice),
            Money.from(maxPrice),
            keyword
        );
    }
}
