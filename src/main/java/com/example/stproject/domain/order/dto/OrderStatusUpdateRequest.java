package com.example.stproject.domain.order.dto;

import com.example.stproject.domain.order.type.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusUpdateRequest {
    @NotNull
    private Long orderId;

    @NotNull
    private OrderStatus status; // COMPLETED or CANCELED
}