package com.example.week01_project.dto.refund;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefundRequest(
        @NotNull Long userId,
        @NotNull Long orderId,
        @NotBlank String reason
) {}
