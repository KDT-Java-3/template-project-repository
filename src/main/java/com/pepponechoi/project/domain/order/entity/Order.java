package com.pepponechoi.project.domain.order.entity;

import com.pepponechoi.project.common.enums.OrderStatus;
import com.pepponechoi.project.domain.product.entity.Product;
import com.pepponechoi.project.domain.refund.entity.Refund;
import com.pepponechoi.project.domain.user.entity.User;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "`order`")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Setter
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order")
    private List<Refund> refunds = new ArrayList<>();

    @Builder
    public Order(
        User user,
        Product product,
        Long quantity,
        String shippingAddress
    ) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.PENDING;

        user.addOrder(this);
        product.addOrder(this);
        product.removeStock(quantity);
    }

    public void changeStatus(OrderStatus status) {
        if (status.equals(OrderStatus.PENDING)) {
            this.status = status;
        }
    }

    public void addRefund(Refund refund) {
        if (!this.refunds.contains(refund)) {
            this.refunds.add(refund);
            refund.setOrder(this);
        }
    }

    public void removeRefund(Refund refund) {
        this.refunds.remove(refund);
        refund.setOrder(null);
    }
}
