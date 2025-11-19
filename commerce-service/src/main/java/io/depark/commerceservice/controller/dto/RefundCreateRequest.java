package io.depark.commerceservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RefundCreateRequest {

    @NotNull(message = "주문 ID는 필수입니다.")
    private Long purchaseId;

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank
    private String reason;
}
