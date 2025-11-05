package com.sparta.project.domain.refund.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefundCreateRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "주문 ID는 필수입니다.")
    private Long purchaseId;

    @NotBlank(message = "환불 사유는 필수입니다.")
    private String reason;
}
