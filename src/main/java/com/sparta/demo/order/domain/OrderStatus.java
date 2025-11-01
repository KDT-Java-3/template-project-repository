package com.sparta.demo.order.domain;

public enum OrderStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    ;

    public boolean cantChange(OrderStatus newOrderStatus) {
        return this == PENDING && newOrderStatus == PENDING;
    }

    public boolean cantCancel() {
        return this != PENDING;
    }
}
