package com.sparta.demo.domain.order.dto.response;

import com.sparta.demo.domain.order.entity.Refund;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RefundResponse {
    private Long id;
    private Long userId;
    private Long orderId;
    private String reason;
    private Refund.RefundStatus status;
    private LocalDateTime requestDate;

    public static RefundResponse buildFromEntity(Refund refund) {
        if (refund == null) return null;

        return RefundResponse.builder()
                .id(refund.getId())
                .userId(refund.getUser().getId())
                .orderId(refund.getOrder().getId())
                .reason(refund.getReason())
                .status(refund.getStatus())
                .requestDate(refund.getCreatedAt())
                .build();
    }
}

