package com.pepponechoi.project.common.enums;

public enum OrderStatus {
    PENDING("대기중"),
    COMPLETED("주문완료"),
    CANCELLED("주문취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
