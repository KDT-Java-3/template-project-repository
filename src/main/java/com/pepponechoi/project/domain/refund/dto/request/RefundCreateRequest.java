package com.pepponechoi.project.domain.refund.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundCreateRequest {
    @NotBlank(message = "유저 ID는 필수입니다.")
    private Long userId;
    @NotBlank(message = "주문 ID는 필수입니다.")
    private Long orderId;
    @NotBlank(message = "최소 사유는 필수입니다.")
    private String reason;
}
