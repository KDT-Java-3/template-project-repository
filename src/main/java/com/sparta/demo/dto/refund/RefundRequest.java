package com.sparta.demo.dto.refund;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "주문 ID는 필수입니다.")
    private Long orderId;

    @NotBlank(message = "환불 사유는 필수입니다.")
    private String reason;

    public RefundRequest(Long userId, Long orderId, String reason) {
        this.userId = userId;
        this.orderId = orderId;
        this.reason = reason;
    }
}
