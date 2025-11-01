package com.sparta.project.common.enums;

public enum RefundStatus {
    PENDING("환불 대기"),
    APPROVED("환불 승인"),
    REJECTED("환불 거절");

    private final String description;

    RefundStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
