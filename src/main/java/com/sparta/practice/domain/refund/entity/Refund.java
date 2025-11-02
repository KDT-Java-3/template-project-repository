package com.sparta.practice.domain.refund.entity;

import com.sparta.practice.domain.order.entity.Order;
import com.sparta.practice.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "refund")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private RefundStatus status = RefundStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void approve() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("대기 상태의 환불만 승인할 수 있습니다.");
        }
        this.status = RefundStatus.APPROVED;

        // 재고 복원
        order.getOrderItems().forEach(item ->
            item.getProduct().increaseStock(item.getQuantity())
        );
    }

    public void reject() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("대기 상태의 환불만 거절할 수 있습니다.");
        }
        this.status = RefundStatus.REJECTED;
    }
}
