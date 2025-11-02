package com.sparta.hodolee246.week01project.controller.request;

import java.math.BigDecimal;

public record ProductRequest(
    String name,
    BigDecimal price,
    Integer stock,
    Long categoryId
) {}
