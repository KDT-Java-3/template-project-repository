package com.example.week01_project.dto.orders;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrdersDtos {
    public record CreateReq(
            @NotNull Long userId,
            @NotNull Long productId,
            @NotNull @Min(1) Integer quantity,
            @NotNull Shipping shipping
    ) {
        public record Shipping(
                @NotNull String recipientName,
                String phone,
                @NotNull String addressLine1,
                String addressLine2,
                String city, String state, String postalCode, String country
        ){}
    }

    public record Resp(Long orderId, String status) {}

    public record ChangeStatusReq(@NotNull String status) {}
}

