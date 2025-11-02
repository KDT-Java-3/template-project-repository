package com.pepponechoi.project.domain.refund.dto.request;

import com.pepponechoi.project.common.enums.RefundStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundUpdateRequest {
    @NotBlank(message = "주문 ID는 필수입니다.")
    private Long orderId;
    @NotBlank(message = "환불 요청 승인여부는 필수입니다.")
    private RefundStatus status;
}
