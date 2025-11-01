package com.sparta.project.common.enums;


public enum PurchaseStatus {
    PENDING("주문 대기"),
    COMPLETED("주문 완료"),
    CANCELED("주문 취소");

    private final String description;

    PurchaseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}