package com.sparta.demo.domain.refund;

import com.sparta.demo.domain.order.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private com.sparta.demo.domain.user.User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RefundStatus status = RefundStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 정적 팩토리 메서드
    public static Refund create(Order order, com.sparta.demo.domain.user.User user, String reason) {
        return Refund.builder()
                .order(order)
                .user(user)
                .reason(reason)
                .build();
    }

    // 비즈니스 로직
    public void approve() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태의 환불만 승인할 수 있습니다.");
        }
        this.status = RefundStatus.APPROVED;
    }

    public void reject() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태의 환불만 거절할 수 있습니다.");
        }
        this.status = RefundStatus.REJECTED;
    }
}
