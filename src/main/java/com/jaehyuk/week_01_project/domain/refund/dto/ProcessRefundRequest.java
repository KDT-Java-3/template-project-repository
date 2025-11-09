package com.jaehyuk.week_01_project.domain.refund.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * 환불 처리 요청 DTO
 *
 * @param action 처리 액션 (APPROVE 또는 REJECT)
 */
@Builder
public record ProcessRefundRequest(
        @NotNull(message = "처리 액션은 필수입니다")
        RefundAction action
) {
    public enum RefundAction {
        APPROVE,    // 환불 승인
        REJECT      // 환불 거절
    }
}
