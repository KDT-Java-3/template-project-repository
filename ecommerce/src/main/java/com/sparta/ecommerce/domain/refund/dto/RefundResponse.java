package com.sparta.ecommerce.domain.refund.dto;

import com.sparta.ecommerce.domain.refund.entity.Refund;
import com.sparta.ecommerce.domain.refund.entity.RefundStatus;
import java.time.LocalDateTime;
import lombok.Generated;

public class RefundResponse {
    String reason;
    RefundStatus status;
    LocalDateTime dateTime;

    public static RefundResponse fromEntity(Refund refund) {
        return builder().reason(refund.getReason()).status(refund.getStatus()).dateTime(refund.getRefundDate()).build();
    }

    @Generated
    RefundResponse(final String reason, final RefundStatus status, final LocalDateTime dateTime) {
        this.reason = reason;
        this.status = status;
        this.dateTime = dateTime;
    }

    @Generated
    public static RefundResponseBuilder builder() {
        return new RefundResponseBuilder();
    }

    @Generated
    public String getReason() {
        return this.reason;
    }

    @Generated
    public RefundStatus getStatus() {
        return this.status;
    }

    @Generated
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    @Generated
    public void setReason(final String reason) {
        this.reason = reason;
    }

    @Generated
    public void setStatus(final RefundStatus status) {
        this.status = status;
    }

    @Generated
    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Generated
    public static class RefundResponseBuilder {
        @Generated
        private String reason;
        @Generated
        private RefundStatus status;
        @Generated
        private LocalDateTime dateTime;

        @Generated
        RefundResponseBuilder() {
        }

        @Generated
        public RefundResponseBuilder reason(final String reason) {
            this.reason = reason;
            return this;
        }

        @Generated
        public RefundResponseBuilder status(final RefundStatus status) {
            this.status = status;
            return this;
        }

        @Generated
        public RefundResponseBuilder dateTime(final LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        @Generated
        public RefundResponse build() {
            return new RefundResponse(this.reason, this.status, this.dateTime);
        }

        @Generated
        public String toString() {
            String var10000 = this.reason;
            return "RefundResponse.RefundResponseBuilder(reason=" + var10000 + ", status=" + String.valueOf(this.status) + ", dateTime=" + String.valueOf(this.dateTime) + ")";
        }
    }
}
