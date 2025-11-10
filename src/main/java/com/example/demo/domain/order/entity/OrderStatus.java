package com.example.demo.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PENDING("대기"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String description;
}
