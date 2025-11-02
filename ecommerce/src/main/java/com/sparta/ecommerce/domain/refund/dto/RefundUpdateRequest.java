package com.sparta.ecommerce.domain.refund.dto;


import com.sparta.ecommerce.domain.refund.entity.RefundStatus;
import lombok.Generated;

public class RefundUpdateRequest {
    Long refundId;
    RefundStatus status;

    @Generated
    public Long getRefundId() {
        return this.refundId;
    }

    @Generated
    public RefundStatus getStatus() {
        return this.status;
    }

    @Generated
    public void setRefundId(final Long refundId) {
        this.refundId = refundId;
    }

    @Generated
    public void setStatus(final RefundStatus status) {
        this.status = status;
    }
}