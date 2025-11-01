package com.sparta.demo.refund.domain;

public enum RefundStatus {
    PENDING,
    APPROVED,
    REJECTED,
    ;

    public boolean cantApprove() {
        return switch (this) {
            case PENDING -> false;
            case APPROVED, REJECTED -> true;
        };
    }

    public boolean cantReject() {
        return switch (this) {
            case PENDING -> false;
            case APPROVED, REJECTED -> true;
        };
    }
}
