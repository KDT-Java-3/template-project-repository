package com.sparta.demo.refund.controller.response;

import com.sparta.demo.refund.domain.Refund;
import com.sparta.demo.refund.domain.RefundStatus;
import java.time.LocalDateTime;

public record RefundFindAllResponse(
        Long id,
        RefundStatus status,
        LocalDateTime refundDateTime,
        String reason
) {

    public static RefundFindAllResponse of(Refund refund) {
        return new RefundFindAllResponse(
                refund.getId(),
                refund.getStatus(),
                refund.getRefundDateTime(),
                refund.getReason()
        );
    }
}
