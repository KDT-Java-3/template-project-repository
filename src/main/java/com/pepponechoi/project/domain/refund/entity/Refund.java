package com.pepponechoi.project.domain.refund.entity;

import com.pepponechoi.project.common.enums.RefundStatus;
import com.pepponechoi.project.domain.order.entity.Order;
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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "refund")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Setter
    private Order order;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    public Refund(User user, Order order, String reason) {
        this.user = user;
        this.order = order;
        this.reason = reason;
        this.status = RefundStatus.PENDING;

        user.addRefund(this);
        order.addRefund(this);
    }

    // 비즈니스 메서드: 환불 승인
    public void approve() {
        if (this.status == RefundStatus.PENDING) {
            this.status = RefundStatus.APPROVED;
            this.order.getProduct().addStock(this.order.getQuantity());
        }
    }

    // 비즈니스 메서드: 환불 거절
    public void reject() {
        if (this.status == RefundStatus.PENDING) {
            this.status = RefundStatus.REJECTED;
        }
    }
}