package com.spartaecommerce.order.domain.entity;

import com.spartaecommerce.common.domain.Money;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem {

    private Long orderItemId;

    private Long productId;
    private Money price;
    private Integer quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderItem createNew(Long productId, Money price, Integer quantity) {
        return new OrderItem(
            null,
            productId,
            price,
            quantity,
            null,
            null
        );
    }

    public Money getSubtotal() {
        return price.multiply(quantity);
    }
}
