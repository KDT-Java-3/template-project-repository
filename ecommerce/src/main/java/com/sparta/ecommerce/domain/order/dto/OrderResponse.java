package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.Order;
import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Generated;

public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private List<OrderProductDto> products;
    private LocalDateTime dateTime;

    public static OrderResponse fromEntity(Order order) {
        List<OrderProductDto> products = order.getOrderProductList().stream().map(OrderProductDto::fromEntity).toList();
        return builder().status(order.getStatus()).dateTime(order.getOrderDate()).products(products).build();
    }

    @Generated
    OrderResponse(final Long id, final OrderStatus status, final List<OrderProductDto> products, final LocalDateTime dateTime) {
        this.id = id;
        this.status = status;
        this.products = products;
        this.dateTime = dateTime;
    }

    @Generated
    public static OrderResponseBuilder builder() {
        return new OrderResponseBuilder();
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
    public List<OrderProductDto> getProducts() {
        return this.products;
    }

    @Generated
    public LocalDateTime getDateTime() {
        return this.dateTime;
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
    public void setProducts(final List<OrderProductDto> products) {
        this.products = products;
    }

    @Generated
    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Generated
    public static class OrderResponseBuilder {
        @Generated
        private Long id;
        @Generated
        private OrderStatus status;
        @Generated
        private List<OrderProductDto> products;
        @Generated
        private LocalDateTime dateTime;

        @Generated
        OrderResponseBuilder() {
        }

        @Generated
        public OrderResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public OrderResponseBuilder status(final OrderStatus status) {
            this.status = status;
            return this;
        }

        @Generated
        public OrderResponseBuilder products(final List<OrderProductDto> products) {
            this.products = products;
            return this;
        }

        @Generated
        public OrderResponseBuilder dateTime(final LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        @Generated
        public OrderResponse build() {
            return new OrderResponse(this.id, this.status, this.products, this.dateTime);
        }

        @Generated
        public String toString() {
            Long var10000 = this.id;
            return "OrderResponse.OrderResponseBuilder(id=" + var10000 + ", status=" + String.valueOf(this.status) + ", products=" + String.valueOf(this.products) + ", dateTime=" + String.valueOf(this.dateTime) + ")";
        }
    }
}
