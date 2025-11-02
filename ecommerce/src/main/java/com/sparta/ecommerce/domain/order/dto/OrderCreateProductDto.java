package com.sparta.ecommerce.domain.order.dto;

import lombok.Generated;

public class OrderCreateProductDto {
    private Long productId;
    private int quantity;

    @Generated
    public Long getProductId() {
        return this.productId;
    }

    @Generated
    public int getQuantity() {
        return this.quantity;
    }

    @Generated
    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    @Generated
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}
