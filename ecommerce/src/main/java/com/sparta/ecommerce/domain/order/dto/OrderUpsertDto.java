package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.Order;
import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import lombok.Generated;

public class OrderUpsertDto {
    private Long id;
    private OrderStatus status;
    private String ShippingAddress;

    public static OrderUpsertDto fromEntity(Order order) {
        return builder().id(order.getId()).status(order.getStatus()).ShippingAddress(order.getShippingAddress()).build();
    }

    @Generated
    OrderUpsertDto(final Long id, final OrderStatus status, final String ShippingAddress) {
        this.id = id;
        this.status = status;
        this.ShippingAddress = ShippingAddress;
    }

    @Generated
    public static OrderUpsertDtoBuilder builder() {
        return new OrderUpsertDtoBuilder();
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public OrderStatus getStatus() {
        return this.status;
    }

    @Generated
    public String getShippingAddress() {
        return this.ShippingAddress;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    @Generated
    public void setShippingAddress(final String ShippingAddress) {
        this.ShippingAddress = ShippingAddress;
    }

    @Generated
    public static class OrderUpsertDtoBuilder {
        @Generated
        private Long id;
        @Generated
        private OrderStatus status;
        @Generated
        private String ShippingAddress;

        @Generated
        OrderUpsertDtoBuilder() {
        }

        @Generated
        public OrderUpsertDtoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public OrderUpsertDtoBuilder status(final OrderStatus status) {
            this.status = status;
            return this;
        }

        @Generated
        public OrderUpsertDtoBuilder ShippingAddress(final String ShippingAddress) {
            this.ShippingAddress = ShippingAddress;
            return this;
        }

        @Generated
        public OrderUpsertDto build() {
            return new OrderUpsertDto(this.id, this.status, this.ShippingAddress);
        }

        @Generated
        public String toString() {
            Long var10000 = this.id;
            return "OrderUpsertDto.OrderUpsertDtoBuilder(id=" + var10000 + ", status=" + String.valueOf(this.status) + ", ShippingAddress=" + this.ShippingAddress + ")";
        }
    }
}

