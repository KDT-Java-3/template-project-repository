package com.sparta.project.domain.refund.dto;

import com.sparta.project.common.enums.RefundStatus;
import com.sparta.project.domain.refund.entity.Refund;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class RefundResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long purchaseId;
    private BigDecimal refundAmount; // 환불 금액 (주문 총액)
    private RefundStatus status;
    private String statusDescription;
    private String reason;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RefundResponse from(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .userId(refund.getUser().getId())
                .username(refund.getUser().getUsername())
                .purchaseId(refund.getPurchase().getId())
                .refundAmount(refund.getPurchase().getTotalPrice())
                .status(refund.getStatus())
                .statusDescription(refund.getStatus().getDescription())
                .reason(refund.getReason())
                .rejectionReason(refund.getRejectionReason())
                .createdAt(refund.getCreatedAt())
                .updatedAt(refund.getUpdatedAt())
                .build();
    }

    public static List<RefundResponse> fromList(List<Refund> refunds) {
        return refunds.stream()
                .map(RefundResponse::from)
                .collect(Collectors.toList());
    }
}