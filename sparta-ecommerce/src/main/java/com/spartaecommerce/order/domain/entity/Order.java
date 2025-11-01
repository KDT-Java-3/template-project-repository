package com.spartaecommerce.order.domain.entity;

import com.spartaecommerce.common.domain.Money;
import com.spartaecommerce.common.util.DateTimeHolder;
import com.spartaecommerce.order.domain.command.OrderCreateCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

    private Long orderId;

    private Long userId;

    private LocalDateTime orderedAt;

    private OrderStatus status;

    private List<OrderItem> orderItems;

    private Money totalAmount;

    private String shippingAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Order createNew(OrderCreateCommand createCommand, DateTimeHolder dateTimeHolder) {
        return new Order(
            null,
            createCommand.userId(),
            dateTimeHolder.getCurrentDateTime(),
            OrderStatus.PENDING,
            new ArrayList<>(),
            Money.zero(),
            createCommand.shippingAddress(),
            null,
            null
        );
    }

    public void addOrderItem(Long productId, Money price, Integer quantity) {
        OrderItem orderItem = OrderItem.createNew(productId, price, quantity);
        this.orderItems.add(orderItem);
        recalculateTotalAmount();
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    private void recalculateTotalAmount() {
        this.totalAmount = orderItems.stream()
            .map(OrderItem::getSubtotal)
            .reduce(Money.zero(), Money::add);
    }
}
