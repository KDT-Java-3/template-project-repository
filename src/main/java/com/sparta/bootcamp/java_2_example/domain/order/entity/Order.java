package com.sparta.bootcamp.java_2_example.domain.order.entity;

import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.common.enums.OrderStatus;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;
import com.sparta.bootcamp.java_2_example.exception.CustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Order(User user, String shippingAddress) {
        this.user = user;
        this.orderNumber = generateOrderNumber();
        this.shippingAddress = shippingAddress;
        this.totalAmount = BigDecimal.ZERO;
    }

    private String generateOrderNumber() {
        return "ORD-" + LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        this.totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void changeStatus(OrderStatus newStatus) {
        if (this.status == OrderStatus.CANCELED || this.status == OrderStatus.COMPLETED) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "완료되거나 취소된 주문의 상태는 변경할 수 없습니다.");
        }

        if (newStatus == OrderStatus.PENDING) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "PENDING 상태로는 변경할 수 없습니다.");
        }

        this.status = newStatus;
    }

    public void cancel() {
        if (this.status != OrderStatus.PENDING) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "PENDING 상태의 주문만 취소할 수 있습니다.");
        }
        this.status = OrderStatus.CANCELED;
    }

    public void refund() {
        if (this.status != OrderStatus.COMPLETED) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "COMPLETED 상태의 주문만 환불할 수 있습니다.");
        }
        this.status = OrderStatus.CANCELED;  // 혹은 REFUNDED 같은 상태가 있다면 그걸로
    }

    public boolean canBeCanceled() {
        return this.status == OrderStatus.CANCELED;
    }
}
