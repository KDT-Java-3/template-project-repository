package com.sprata.sparta_ecommerce.dto.param;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class SearchProductDto {

    private static final long MIN_PRICE = 0;
    private static final long MAX_PRICE = 999_999_999_999L;

    @Builder.Default
    private Long categoryId = 0L;

    @Builder.Default
    private Long minPrice = MIN_PRICE;

    @Builder.Default
    private Long maxPrice = MAX_PRICE;

    @Builder.Default
    private String keyword = "";

    public SearchProductDto(Long categoryId, Long minPrice, Long maxPrice, String keyword) {
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.keyword = keyword;
    }
}
