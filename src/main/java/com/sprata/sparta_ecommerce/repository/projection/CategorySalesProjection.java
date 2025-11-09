package com.sprata.sparta_ecommerce.repository.projection;

import lombok.Getter;

@Getter
public class CategorySalesProjection {
    private Long categoryId;
    private String categoryName;
    private int categorySalesCount;

    public CategorySalesProjection(Long categoryId, String categoryName, int categorySalesCount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categorySalesCount = categorySalesCount;
    }
}
