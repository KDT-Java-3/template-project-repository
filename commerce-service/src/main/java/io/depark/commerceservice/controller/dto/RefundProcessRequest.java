package io.depark.commerceservice.controller.dto;

import io.depark.commerceservice.entity.enums.RefundStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RefundProcessRequest {

    @NotNull(message = "처리 상태는 필수입니다.")
    private RefundStatus status;
}
