package com.sparta.ecommerce.domain.refund.entity;

import com.sparta.ecommerce.domain.order.entity.Order;
import com.sparta.ecommerce.domain.user.entity.User;
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
import java.time.LocalDateTime;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @Column(nullable = false)
    private String reason;
    @Column(name = "refund_date")
    private LocalDateTime refundDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status;

    @Builder
    public Refund(User user, Order order, String reason) {
        this.status = RefundStatus.pending;
        this.user = user;
        this.order = order;
        this.reason = reason;
        this.refundDate = LocalDateTime.now();
    }

    public void approve() {
        if (this.status == RefundStatus.pending) {
            this.status = RefundStatus.approved;
        } else {
            throw new IllegalStateException("대기상태의 환불만 승인할 수 있습니다.");
        }
    }

    public void reject() {
        if (this.status == RefundStatus.pending) {
            this.status = RefundStatus.rejected;
        } else {
            throw new IllegalStateException("대기상태의 환불만 반려할 수 있습니다.");
        }
    }
}

