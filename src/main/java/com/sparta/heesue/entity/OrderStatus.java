package com.sparta.heesue.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("주문대기"),
    CONFIRMED("주문확인"),
    SHIPPED("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("주문취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

}