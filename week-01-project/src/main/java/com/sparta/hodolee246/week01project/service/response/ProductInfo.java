package com.sparta.hodolee246.week01project.service.response;

import java.math.BigDecimal;

public record ProductInfo(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    Long categoryId,
    String categoryName
) {}