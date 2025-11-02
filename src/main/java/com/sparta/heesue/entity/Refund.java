package com.sparta.heesue.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(name = "refund_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal refundAmount;

    @Column(name = "refund_reason", length = 500)
    private String refundReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_status", nullable = false, length = 20)
    private RefundStatus refundStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Refund(OrderItem orderItem, BigDecimal refundAmount, String refundReason, RefundStatus refundStatus) {
        this.orderItem = orderItem;
        this.refundAmount = refundAmount;
        this.refundReason = refundReason;
        this.refundStatus = refundStatus;
    }

    // 환불 상태 변경 메서드
    public void updateStatus(RefundStatus newStatus) {
        this.refundStatus = newStatus;
    }

    // 환불 승인
    public void approve() {
        this.refundStatus = RefundStatus.APPROVED;
    }

    // 환불 거부
    public void reject() {
        this.refundStatus = RefundStatus.REJECTED;
    }

    // 환불 완료
    public void complete() {
        this.refundStatus = RefundStatus.COMPLETED;
    }
}