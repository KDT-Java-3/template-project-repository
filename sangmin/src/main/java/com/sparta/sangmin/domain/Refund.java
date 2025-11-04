package com.sparta.sangmin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refund")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RefundStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Refund(User user, Order order, String reason) {
        this.user = user;
        this.order = order;
        this.reason = reason;
        this.status = RefundStatus.PENDING;
    }

    public void approve() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태의 환불 요청만 승인할 수 있습니다.");
        }
        this.status = RefundStatus.APPROVED;
    }

    public void reject() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태의 환불 요청만 거절할 수 있습니다.");
        }
        this.status = RefundStatus.REJECTED;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum RefundStatus {
        PENDING, APPROVED, REJECTED
    }
}
