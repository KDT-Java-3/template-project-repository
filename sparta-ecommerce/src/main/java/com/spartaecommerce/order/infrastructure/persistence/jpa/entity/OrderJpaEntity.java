package com.spartaecommerce.order.infrastructure.persistence.jpa.entity;

import com.spartaecommerce.order.domain.entity.Order;
import com.spartaecommerce.order.domain.entity.OrderItem;
import com.spartaecommerce.order.domain.entity.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_id")
    private List<OrderItemJpaEntity> orderItems = new ArrayList<>();

    public static OrderJpaEntity from(Order order) {
        List<OrderItemJpaEntity> orderItemJpaEntities = order.getOrderItems().stream()
            .map(OrderItemJpaEntity::from)
            .toList();

        return OrderJpaEntity.builder()
            .orderId(order.getOrderId())
            .userId(order.getUserId())
            .status(order.getStatus())
            .shippingAddress(order.getShippingAddress())
            .createdAt(order.getCreatedAt())
            .updatedAt(order.getUpdatedAt())
            .orderItems(orderItemJpaEntities)
            .build();
    }

    public Order toDomain() {
        List<OrderItem> orderItemList = this.orderItems.stream()
            .map(OrderItemJpaEntity::toDomain)
            .toList();

        return Order.builder()
            .orderId(this.orderId)
            .userId(this.userId)
            .status(this.status)
            .orderItems(orderItemList)
            .shippingAddress(this.shippingAddress)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
    }
}
