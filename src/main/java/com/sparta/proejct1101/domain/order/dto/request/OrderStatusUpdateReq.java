package com.sparta.proejct1101.domain.order.dto.request;

import com.sparta.proejct1101.domain.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateReq(
        @NotNull
        OrderStatus status
) {
}
