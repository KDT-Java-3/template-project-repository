package com.sparta.bootcamp.work.domain.refund.dto;

import com.sparta.bootcamp.work.common.enums.RefundStatus;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest {

    Long refundId;
    Long orderId;
    Long userId;
    String reason;
    RefundStatus status;

}
