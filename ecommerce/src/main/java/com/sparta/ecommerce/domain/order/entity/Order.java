package com.sparta.ecommerce.domain.order.entity;

import com.sparta.ecommerce.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "shipping_address", nullable = false, length = 255)
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList;

    @Builder
    public Order(User user, String shippingAddress) {
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.pending;
        this.orderDate = LocalDateTime.now();
        this.orderProductList = new ArrayList<>();
    }

    public void complete() {
        if (this.status == OrderStatus.pending) {
            this.status = OrderStatus.completed;
        } else {
            throw new IllegalStateException("대기상태의 주문만 완료할 수 있습니다.");
        }
    }

    public void cancel() {
        if (this.status == OrderStatus.pending) {
            this.status = OrderStatus.canceled;
        } else {
            throw new IllegalStateException("대기상태의 주문만 취소할 수 있습니다.");
        }
    }

    public void addProduct(OrderProduct product) {
        this.orderProductList.add(product);
        product.connectOrder(this);
    }
}
