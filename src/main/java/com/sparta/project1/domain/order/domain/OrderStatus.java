package com.sparta.project1.domain.order.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum OrderStatus {
    PENDING, COMPLETED, CANCELED;

    public static OrderStatus findByValue(String value) {
        return Arrays.stream(OrderStatus.values())
                .filter(v -> v.name().equals(value))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("orderStatus not found"));
    }
}
