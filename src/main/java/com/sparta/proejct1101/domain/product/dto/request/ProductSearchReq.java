package com.sparta.proejct1101.domain.product.dto.request;

public record ProductSearchReq(
        Long categoryId,
        Integer minPrice,
        Integer maxPrice,
        String keyword
) {
}
