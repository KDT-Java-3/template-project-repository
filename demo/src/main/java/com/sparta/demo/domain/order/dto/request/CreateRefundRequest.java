package com.sparta.demo.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateRefundRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long orderId;

    @NotBlank
    @Size(max = 500)
    private String reason;
}

