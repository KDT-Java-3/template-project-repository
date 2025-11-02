package com.sparta.ecommerce.domain.product.dto;

import java.math.BigDecimal;
import lombok.Generated;

public class ProductReadRequest {
    private Long id;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String name;

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public Long getCategoryId() {
        return this.categoryId;
    }

    @Generated
    public BigDecimal getMinPrice() {
        return this.minPrice;
    }

    @Generated
    public BigDecimal getMaxPrice() {
        return this.maxPrice;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    @Generated
    public void setMinPrice(final BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    @Generated
    public void setMaxPrice(final BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public ProductReadRequest(final Long id, final Long categoryId, final BigDecimal minPrice, final BigDecimal maxPrice, final String name) {
        this.id = id;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.name = name;
    }
}
