package com.sparta.practice.domain.refund.dto;

import com.sparta.practice.domain.refund.entity.Refund;
import com.sparta.practice.domain.refund.entity.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class RefundResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long orderId;
    private String reason;
    private RefundStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RefundResponse from(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .userId(refund.getUser().getId())
                .userName(refund.getUser().getName())
                .orderId(refund.getOrder().getId())
                .reason(refund.getReason())
                .status(refund.getStatus())
                .createdAt(refund.getCreatedAt())
                .updatedAt(refund.getUpdatedAt())
                .build();
    }
}
