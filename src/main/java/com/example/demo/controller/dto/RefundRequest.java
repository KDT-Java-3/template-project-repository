package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundRequest {
    @NotNull(message = "사용자 ID는 필수입니다")
    private Long userId;

    @NotNull(message = "주문 ID는 필수입니다")
    private Long orderId;

    @NotBlank(message = "환불 사유는 필수입니다")
    @Size(max = 500, message = "환불 사유는 500자를 초과할 수 없습니다")
    private String reason;
}

