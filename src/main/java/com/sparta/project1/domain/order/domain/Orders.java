package com.sparta.project1.domain.order.domain;

import com.sparta.project1.domain.BaseEntity;
import com.sparta.project1.domain.product.domain.ProductOrderInfo;
import com.sparta.project1.domain.user.domain.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(columnDefinition = "mediumText", nullable = false)
    private String shippingAddress;

    @BatchSize(size = 50)
    @OneToMany(mappedBy = "orders")
    private List<ProductOrder> productOrders = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    Orders(Users user, BigDecimal totalPrice, String shippingAddress, OrderStatus status) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
        this.status = status;
    }

    // TODO: totalPrice 계산은 어떻게?
    public static Orders register(Users user, String shippingAddress, List<ProductOrderInfo> productOrderInfos) {
        BigDecimal totalPrice = FeeCalculator.calculateTotalPrice(productOrderInfos);
        return new Orders(user, totalPrice, shippingAddress, OrderStatus.PENDING);
    }

    public void changeStatus(String status) {
        this.status = OrderStatus.findByValue(status);
    }

    public void cancelOrder() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalArgumentException("cancel is only available when status is PENDING");
        }
        this.status = OrderStatus.CANCELED;
    }
}
