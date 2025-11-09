package com.jaehyuk.week_01_project.domain.refund.dto;

import com.jaehyuk.week_01_project.domain.refund.entity.Refund;
import com.jaehyuk.week_01_project.domain.refund.enums.RefundStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 환불 요청 응답 DTO
 *
 * @param refundId 환불 요청 ID
 * @param purchaseId 주문 ID
 * @param userId 사용자 ID
 * @param reason 환불 사유
 * @param status 환불 상태
 * @param totalPrice 환불 금액 (주문 총액)
 * @param createdAt 환불 요청 시각
 */
@Builder
public record RefundResponse(
        Long refundId,
        Long purchaseId,
        Long userId,
        String reason,
        RefundStatus status,
        BigDecimal totalPrice,
        LocalDateTime createdAt
) {
    public static RefundResponse from(Refund refund) {
        return RefundResponse.builder()
                .refundId(refund.getId())
                .purchaseId(refund.getPurchase().getId())
                .userId(refund.getUser().getId())
                .reason(refund.getReason())
                .status(refund.getStatus())
                .totalPrice(refund.getPurchase().getTotalPrice())
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
