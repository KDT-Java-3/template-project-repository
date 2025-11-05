package com.sparta.ecommerce.domain.refund.dto;

import com.sparta.ecommerce.domain.refund.entity.Refund;
import com.sparta.ecommerce.domain.refund.entity.RefundStatus;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefundResponse {
    String reason;
    RefundStatus status;
    LocalDateTime dateTime;

    public static RefundResponse fromEntity(Refund refund) {
        return builder().reason(refund.getReason()).status(refund.getStatus()).dateTime(refund.getRefundDate()).build();
    }
}
