package com.sparta.bootcamp.java_2_example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundRequest {
    @NotNull(message = "사용자 ID는 필수입니다")
    private Long userId;

    @NotNull(message = "주문 ID는 필수입니다")
    private Long orderId;

    @NotBlank(message = "환불 사유는 필수입니다")
    private String reason;
}
