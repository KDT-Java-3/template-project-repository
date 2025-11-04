package com.sparta.proejct1101.domain.product.dto.request;

public record ProductReq (
        Long categoryId,
        String prodName,
        Integer price,
        Integer stock,
        String description
) {
}

