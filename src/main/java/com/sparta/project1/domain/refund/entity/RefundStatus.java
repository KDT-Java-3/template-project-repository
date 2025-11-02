package com.sparta.project1.domain.refund.entity;

import com.sparta.project1.domain.order.domain.OrderStatus;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum RefundStatus {
    PENDING, APPROVED, REJECTED;

    public static RefundStatus findByValue(String value) {
        return Arrays.stream(RefundStatus.values())
                .filter(v -> v.name().equals(value))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("refundStatus not found"));
    }
}
