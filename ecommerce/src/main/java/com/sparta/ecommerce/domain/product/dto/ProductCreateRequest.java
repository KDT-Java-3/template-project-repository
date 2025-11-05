package com.sparta.ecommerce.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateRequest {
    public String name;
    public BigDecimal price;
    public Integer stock;
    public Long categoryId;
    public String description;
}
