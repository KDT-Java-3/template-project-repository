package com.sparta.heesue.entity;

import lombok.Getter;

@Getter
public enum RefundStatus {
    REQUESTED("환불요청"),
    APPROVED("환불승인"),
    REJECTED("환불거부"),
    COMPLETED("환불완료");

    private final String description;

    RefundStatus(String description) {
        this.description = description;
    }
}