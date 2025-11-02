package com.pepponechoi.project.domain.refund.dto.response;

import com.pepponechoi.project.common.enums.RefundStatus;
import java.time.LocalDate;

public record RefundResponse(
    Long id,
    Long orderId,
    RefundStatus status,
    LocalDate createdAt,
    String reason
) {

}
