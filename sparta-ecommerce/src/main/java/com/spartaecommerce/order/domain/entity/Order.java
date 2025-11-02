package com.spartaecommerce.order.domain.entity;

import com.spartaecommerce.common.domain.Money;
import com.spartaecommerce.order.domain.command.OrderCreateCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

    private Long orderId;

    private Long userId;

    private OrderStatus status;

    private List<OrderItem> orderItems;

    private String shippingAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Order createNew(OrderCreateCommand createCommand) {
        return new Order(
            null,
            createCommand.userId(),
            OrderStatus.PENDING,
            new ArrayList<>(),
//            Money.zero(),
            createCommand.shippingAddress(),
            null,
            null
        );
    }

    public void addOrderItem(
        Long productId,
        String productName,
        Money price,
        Integer quantity
    ) {
        OrderItem orderItem = OrderItem.createNew(
            productId,
            productName,
            price,
            quantity
        );
        this.orderItems.add(orderItem);
    }

    public BigDecimal calculateTotalAmount() {
        Money totalAmount = this.orderItems.stream()
            .map(OrderItem::getSubtotal)
            .reduce(Money.zero(), Money::add);

        return totalAmount.amount();
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }
}
