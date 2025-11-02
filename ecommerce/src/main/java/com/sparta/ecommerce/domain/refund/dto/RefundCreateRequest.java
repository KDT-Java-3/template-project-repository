package com.sparta.ecommerce.domain.refund.dto;

import lombok.Generated;

public class RefundCreateRequest {
    Long userId;
    Long orderId;
    String reason;

    @Generated
    public Long getUserId() {
        return this.userId;
    }

    @Generated
    public Long getOrderId() {
        return this.orderId;
    }

    @Generated
    public String getReason() {
        return this.reason;
    }

    @Generated
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Generated
    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    @Generated
    public void setReason(final String reason) {
        this.reason = reason;
    }
}

