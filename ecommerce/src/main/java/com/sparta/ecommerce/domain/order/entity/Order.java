package com.sparta.ecommerce.domain.order.entity;

import com.sparta.ecommerce.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

@Entity
@Table(
        name = "orders"
)
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
    @Column(
            name = "shipping_address",
            nullable = false,
            length = 255
    )
    private String shippingAddress;
    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status;
    @Column(
            name = "order_date"
    )
    private LocalDateTime orderDate;
    @OneToMany(
            mappedBy = "order",
            orphanRemoval = true,
            cascade = {CascadeType.ALL}
    )
    private List<OrderProduct> orderProductList;

    public Order(User user, String shippingAddress) {
        this.status = OrderStatus.pending;
        this.orderDate = LocalDateTime.now();
        this.orderProductList = new ArrayList();
        this.user = user;
        this.shippingAddress = shippingAddress;
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
            throw new IllegalStateException("대기상태의 주문만 완료할 수 있습니다.");
        }
    }

    public void addProduct(OrderProduct product) {
        this.orderProductList.add(product);
        product.connectOrder(this);
    }

    @Generated
    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    @Generated
    protected Order() {
        this.status = OrderStatus.pending;
        this.orderDate = LocalDateTime.now();
        this.orderProductList = new ArrayList();
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public User getUser() {
        return this.user;
    }

    @Generated
    public String getShippingAddress() {
        return this.shippingAddress;
    }

    @Generated
    public OrderStatus getStatus() {
        return this.status;
    }

    @Generated
    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }

    @Generated
    public List<OrderProduct> getOrderProductList() {
        return this.orderProductList;
    }

    @Generated
    public static class OrderBuilder {
        @Generated
        private User user;
        @Generated
        private String shippingAddress;

        @Generated
        OrderBuilder() {
        }

        @Generated
        public OrderBuilder user(final User user) {
            this.user = user;
            return this;
        }

        @Generated
        public OrderBuilder shippingAddress(final String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        @Generated
        public Order build() {
            return new Order(this.user, this.shippingAddress);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.user);
            return "Order.OrderBuilder(user=" + var10000 + ", shippingAddress=" + this.shippingAddress + ")";
        }
    }
}
