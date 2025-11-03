package com.sparta.proejct1101.domain.order.entity;

import com.sparta.proejct1101.domain.common.entity.BaseEntity;
import com.sparta.proejct1101.domain.product.entity.Product;
import com.sparta.proejct1101.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void updateStatus(OrderStatus newStatus) {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be updated. Current status: " + this.status);
        }
        if (newStatus != OrderStatus.COMPLETED && newStatus != OrderStatus.CANCELED) {
            throw new IllegalArgumentException("Status can only be changed to COMPLETED or CANCELED. Requested: " + newStatus);
        }
        this.status = newStatus;
    }
}
