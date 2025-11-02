package com.spartaecommerce.order.domain.entity;

import com.spartaecommerce.common.domain.Money;
import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
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
        return Order.builder()
            .userId(createCommand.userId())
            .status(OrderStatus.PENDING)
            .orderItems(new ArrayList<>())
            .shippingAddress(createCommand.shippingAddress())
            .build();
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

    public void updateOrderStatus(OrderStatus newStatus) {
        validateStatusTransition(this.status, newStatus);
        this.status = newStatus;
    }

    public void complete() {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException(
                "PENDING 상태의 주문만 완료할 수 있습니다. 현재 상태: " + this.status
            );
        }
        this.status = OrderStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("완료된 주문은 취소할 수 없습니다.");
        }
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }
        this.status = OrderStatus.CANCELED;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    private void validateStatusTransition(OrderStatus from, OrderStatus to) {
        if (from == to) {
            return;
        }

        if (from == OrderStatus.PENDING) {
            if (to == OrderStatus.COMPLETED || to == OrderStatus.CANCELED) {
                return;
            }

            throw new BusinessException(
                ErrorCode.ORDER_INVALID_STATE_TRANSITION,
                from + " -> " + to
            );
        }

        throw new BusinessException(
            ErrorCode.ORDER_INVALID_STATE_TRANSITION,
            from + " -> " + to
        );
    }
}
