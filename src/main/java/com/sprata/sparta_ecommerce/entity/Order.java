package com.sprata.sparta_ecommerce.entity;

import com.sprata.sparta_ecommerce.listener.OrderListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders") // 'order' is a reserved keyword in SQL
@EntityListeners(OrderListener.class)
public class Order extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column
    private LocalDate orderDate;

    @Builder
    public Order(Long userId, Product product, int quantity, String shippingAddress, LocalDate orderDate) {
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.PENDING;
        this.orderDate = orderDate;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}
