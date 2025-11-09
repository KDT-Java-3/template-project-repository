package com.jaehyuk.week_01_project.domain.refund.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * 환불 요청 생성 DTO
 *
 * @param purchaseId 주문 ID (필수)
 * @param reason 환불 사유 (필수)
 */
@Builder
public record CreateRefundRequest(
        @NotNull(message = "주문 ID는 필수입니다")
        Long purchaseId,

        @NotBlank(message = "환불 사유는 필수입니다")
        String reason
) {
}
