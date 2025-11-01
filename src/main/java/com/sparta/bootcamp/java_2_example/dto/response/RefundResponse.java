package com.sparta.bootcamp.java_2_example.dto.response;

import com.sparta.bootcamp.java_2_example.common.enums.RefundStatus;
import com.sparta.bootcamp.java_2_example.domain.refund.entity.Refund;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundResponse {
    private Long id;
    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String reason;
    private RefundStatus status;
    private BigDecimal refundAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RefundResponse from(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .orderId(refund.getOrder().getId())
                .orderNumber(refund.getOrder().getOrderNumber())
                .userId(refund.getUser().getId())
                .reason(refund.getReason())
                .status(refund.getStatus())
                .refundAmount(refund.getRefundAmount())
                .createdAt(refund.getCreatedAt())
                .updatedAt(refund.getUpdatedAt())
                .build();
    }
}
