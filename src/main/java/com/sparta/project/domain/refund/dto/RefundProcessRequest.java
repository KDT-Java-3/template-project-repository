package com.sparta.project.domain.refund.dto;

import com.sparta.project.common.enums.RefundStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefundProcessRequest {

    @NotNull(message = "처리할 환불 상태는 필수입니다.")
    private RefundStatus status; // APPROVED 또는 REJECTED

    private String rejectionReason; // 거절 시에만 필요
}
