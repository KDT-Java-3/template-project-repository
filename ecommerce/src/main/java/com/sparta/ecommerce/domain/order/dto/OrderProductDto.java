package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.OrderProduct;
import lombok.Generated;

public class OrderProductDto {
    private Long productId;
    private int quantity;
    private String name;
    private String description;

    public static OrderProductDto fromEntity(OrderProduct product) {
        return builder().productId(product.getId()).quantity(product.getQuantity()).description(product.getProduct().getDescription()).name(product.getProduct().getName()).build();
    }

    @Generated
    public static OrderProductDtoBuilder builder() {
        return new OrderProductDtoBuilder();
    }

    @Generated
    public Long getProductId() {
        return this.productId;
    }

    @Generated
    public int getQuantity() {
        return this.quantity;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    @Generated
    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public void setDescription(final String description) {
        this.description = description;
    }

    @Generated
    public OrderProductDto() {
    }

    @Generated
    public OrderProductDto(final Long productId, final int quantity, final String name, final String description) {
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
    }

    @Generated
    public static class OrderProductDtoBuilder {
        @Generated
        private Long productId;
        @Generated
        private int quantity;
        @Generated
        private String name;
        @Generated
        private String description;

        @Generated
        OrderProductDtoBuilder() {
        }

        @Generated
        public OrderProductDtoBuilder productId(final Long productId) {
            this.productId = productId;
            return this;
        }

        @Generated
        public OrderProductDtoBuilder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Generated
        public OrderProductDtoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public OrderProductDtoBuilder description(final String description) {
            this.description = description;
            return this;
        }

        @Generated
        public OrderProductDto build() {
            return new OrderProductDto(this.productId, this.quantity, this.name, this.description);
        }

        @Generated
        public String toString() {
            return "OrderProductDto.OrderProductDtoBuilder(productId=" + this.productId + ", quantity=" + this.quantity + ", name=" + this.name + ", description=" + this.description + ")";
        }
    }
}
