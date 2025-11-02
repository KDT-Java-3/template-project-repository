package com.example.week01_project.dto.refund;

import jakarta.validation.constraints.NotNull;

public class RefundDtos {
    public record RequestReq(
            @NotNull Long userId,
            @NotNull Long orderId,
            String reason
    ) {}

    public record ProcessReq(
            @NotNull String action, // approve / reject
            Long adminUserId
    ) {}

    public record Resp(Long refundId, String status) {}
}
