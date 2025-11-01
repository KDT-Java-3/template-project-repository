package com.spartaecommerce.category.presentation.controller.dto.request;

import com.spartaecommerce.category.domain.query.CategorySearchQuery;

public record CategorySearchRequest(
    Long productId
) {
    public CategorySearchQuery toQuery() {
        return new CategorySearchQuery(productId);
    }
}
