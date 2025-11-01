package com.spartaecommerce.order.infrastructure.persistence.jpa.entity;

import com.spartaecommerce.order.domain.entity.OrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    private Long productId;
    private BigDecimal price;
    private Integer quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderItemJpaEntity from(OrderItem orderItem) {
        return new OrderItemJpaEntity(
            orderItem.getOrderItemId(),
            null,
            orderItem.getProductId(),
            orderItem.getPrice().amount(),
            orderItem.getQuantity(),
            orderItem.getCreatedAt(),
            orderItem.getUpdatedAt()
        );
    }
}
