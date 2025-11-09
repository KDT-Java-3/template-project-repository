package com.example.week01_project.dto.product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price, int stock, Long categoryId) {}