package com.sparta.demo.domain.order.dto.request;

import com.sparta.demo.domain.order.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateOrderStatusRequest {
    @Setter
    private Long id;
    @NotNull
    private Order.OrderStatus oldStatus;
    @NotNull
    private Order.OrderStatus newStatus;

    public boolean isChangeable() {
        switch (this.oldStatus){
            case PENDING -> {
                return this.newStatus.equals(Order.OrderStatus.COMPLETED)
                    || this.newStatus.equals(Order.OrderStatus.CANCELED);
            }
            case COMPLETED, CANCELED -> {return false;}
            default -> {return false;}
        }
    }
}
