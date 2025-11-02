package com.sparta.ecommerce.domain.order.dto;

import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import lombok.Generated;

public class OrderUpdateStateRequest {
    private Long id;
    private OrderStatus orderStatus;

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
