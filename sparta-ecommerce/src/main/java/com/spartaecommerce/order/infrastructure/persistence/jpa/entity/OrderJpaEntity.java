package com.spartaecommerce.order.infrastructure.persistence.jpa.entity;

import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.entity.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;

    private LocalDateTime orderedAt;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private String shippingAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_id")  // OrderItem 테이블의 FK
    private List<OrderItemJpaEntity> orderItems = new ArrayList<>();

    public static OrderJpaEntity from(Order order) {
        List<OrderItemJpaEntity> orderItemJpaEntities = order.getOrderItems().stream()
            .map(OrderItemJpaEntity::from)
            .toList();

        return new OrderJpaEntity(
            order.getOrderId(),
            order.getUserId(),
            order.getOrderedAt(),
            order.getStatus(),
            order.getTotalAmount().amount(),
            order.getShippingAddress(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            orderItemJpaEntities
        );
    }
}
