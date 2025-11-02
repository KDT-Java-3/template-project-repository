package com.spartaecommerce.order.infrastructure.persistence.jpa.entity;

import com.spartaecommerce.common.domain.Money;
import com.spartaecommerce.order.domain.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderItemJpaEntity from(OrderItem orderItem) {
        return OrderItemJpaEntity.builder()
            .orderItemId(orderItem.getOrderItemId())
            .productId(orderItem.getProductId())
            .productName(orderItem.getProductName())
            .productPrice(orderItem.getProductPrice().amount())
            .quantity(orderItem.getQuantity())
            .createdAt(orderItem.getCreatedAt())
            .updatedAt(orderItem.getUpdatedAt())
            .build();
    }

    public OrderItem toDomain() {
        return OrderItem.builder()
            .orderItemId(this.orderId)
            .productId(this.productId)
            .productName(this.productName)
            .productPrice(Money.from(this.productPrice))
            .quantity(this.quantity)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
    }
}
