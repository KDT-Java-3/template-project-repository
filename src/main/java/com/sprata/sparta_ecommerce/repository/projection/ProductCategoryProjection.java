package com.sprata.sparta_ecommerce.repository.projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductCategoryProjection {
    private String categoryName;
    private BigDecimal count;
    private BigDecimal sum;

    public ProductCategoryProjection(String categoryName, BigDecimal count, BigDecimal sum) {
        this.categoryName = categoryName;
        this.count = count;
        this.sum = sum;
    }
}
