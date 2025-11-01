package com.sparta.commerce.management.dto.response.refund;

import com.sparta.commerce.management.entity.Refund;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundResponse {
    String status;
    LocalDateTime rgDt;
    String reason;

    public static RefundResponse getRefundResponse(Refund refund) {
        return RefundResponse.builder()
                .status(refund.getStatus())
                .reason(refund.getReason())
                .rgDt(refund.getRgDt())
            .build();
    }

    public static List<RefundResponse> getRefundResponseList(List<Refund> refundList) {
        return refundList.stream().map(RefundResponse::getRefundResponse).toList();
    }

}
