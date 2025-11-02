package com.sparta.ecommerce.domain.product.dto;

import java.math.BigDecimal;
import lombok.Generated;

public class ProductUpdateRequest {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private String description;

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public BigDecimal getPrice() {
        return this.price;
    }

    @Generated
    public Integer getStock() {
        return this.stock;
    }

    @Generated
    public Long getCategoryId() {
        return this.categoryId;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    @Generated
    public void setStock(final Integer stock) {
        this.stock = stock;
    }

    @Generated
    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    @Generated
    public void setDescription(final String description) {
        this.description = description;
    }
}
