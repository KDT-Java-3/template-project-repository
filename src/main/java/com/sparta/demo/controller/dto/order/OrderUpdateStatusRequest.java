package com.sparta.demo.controller.dto.order;

import com.sparta.demo.domain.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderUpdateStatusRequest {

    @NotNull(message = "주문 상태는 필수입니다.")
    private OrderStatus status;

    public OrderUpdateStatusRequest(OrderStatus status) {
        this.status = status;
    }
}
