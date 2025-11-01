package com.sparta.demo.order.domain;

import com.sparta.demo.product.domain.Product;
import com.sparta.demo.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@Table(name = "orders")
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(value = NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(value = NO_CONSTRAINT))
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String shippingAddress;

    @Builder.Default
    @Enumerated(STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    private LocalDateTime lastUpdateTime;

    public static Order create(User user, Product product, int quantity, String shippingAddress) {
        product.decreaseStock(quantity);
        return Order.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .shippingAddress(shippingAddress)
                .build();
    }

    public void changeStatus(OrderStatus newOrderStatus) {
        if (this.orderStatus.cantChange(newOrderStatus)) {
            throw new IllegalArgumentException("변경할 수 없는 주문 상태입니다.");
        }
        this.orderStatus = newOrderStatus;
        this.lastUpdateTime = LocalDateTime.now();
    }

    public void cancel() {
        if (this.orderStatus.cantCancel()) {
            throw new IllegalStateException("주문을 취소할 수 없는 상태입니다.");
        }
        product.increaseStock(quantity);
        this.orderStatus = OrderStatus.CANCELLED;
        this.lastUpdateTime = LocalDateTime.now();
    }

    public void refund() {
        product.increaseStock(quantity);
        this.orderStatus = OrderStatus.CANCELLED;
        this.lastUpdateTime = LocalDateTime.now();
    }
}
