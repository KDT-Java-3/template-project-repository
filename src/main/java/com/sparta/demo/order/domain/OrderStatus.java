package com.sparta.demo.order.domain;

public enum OrderStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    ;

    public boolean cantChange(OrderStatus newOrderStatus) {
        return switch (this) {
            case PENDING -> newOrderStatus != OrderStatus.PENDING;
            case COMPLETED, CANCELLED -> false;
        };
    }

    public boolean cantCancel() {
        return switch (this) {
            case PENDING -> false;
            case COMPLETED, CANCELLED -> true;
        };
    }
}
