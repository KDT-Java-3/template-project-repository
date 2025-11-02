package com.sparta.hodolee246.week01project.service.request;

import java.math.BigDecimal;

public record ProductDto(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Long categoryId
) {}
