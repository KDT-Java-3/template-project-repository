package com.sparta.ecommerce.domain.order.dto;

import java.util.List;
import lombok.Generated;

public class OrderCreateRequest {
    private String name;
    private Long userId;
    private List<OrderCreateProductDto> products;
    private String shippingAddress;

    @Generated
    OrderCreateRequest(final String name, final Long userId, final List<OrderCreateProductDto> products, final String shippingAddress) {
        this.name = name;
        this.userId = userId;
        this.products = products;
        this.shippingAddress = shippingAddress;
    }

    @Generated
    public static OrderCreateRequestBuilder builder() {
        return new OrderCreateRequestBuilder();
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Long getUserId() {
        return this.userId;
    }

    @Generated
    public List<OrderCreateProductDto> getProducts() {
        return this.products;
    }

    @Generated
    public String getShippingAddress() {
        return this.shippingAddress;
    }

    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Generated
    public void setProducts(final List<OrderCreateProductDto> products) {
        this.products = products;
    }

    @Generated
    public void setShippingAddress(final String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Generated
    public static class OrderCreateRequestBuilder {
        @Generated
        private String name;
        @Generated
        private Long userId;
        @Generated
        private List<OrderCreateProductDto> products;
        @Generated
        private String shippingAddress;

        @Generated
        OrderCreateRequestBuilder() {
        }

        @Generated
        public OrderCreateRequestBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public OrderCreateRequestBuilder userId(final Long userId) {
            this.userId = userId;
            return this;
        }

        @Generated
        public OrderCreateRequestBuilder products(final List<OrderCreateProductDto> products) {
            this.products = products;
            return this;
        }

        @Generated
        public OrderCreateRequestBuilder shippingAddress(final String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        @Generated
        public OrderCreateRequest build() {
            return new OrderCreateRequest(this.name, this.userId, this.products, this.shippingAddress);
        }

        @Generated
        public String toString() {
            String var10000 = this.name;
            return "OrderCreateRequest.OrderCreateRequestBuilder(name=" + var10000 + ", userId=" + this.userId + ", products=" + String.valueOf(this.products) + ", shippingAddress=" + this.shippingAddress + ")";
        }
    }
}
