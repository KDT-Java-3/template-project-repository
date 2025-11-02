package com.sparta.demo.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table
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

    @Column(nullable = false, length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Setter
    private RefundStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void approve() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("환불 승인은 PENDING 상태에서만 가능합니다.");
        }
        this.status = RefundStatus.APPROVED;
        
        // 재고 복원
        Product product = this.order.getProduct();
        product.setStock(product.getStock() + this.order.getQuantity());
    }

    public void reject() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException("환불 거절은 PENDING 상태에서만 가능합니다.");
        }
        this.status = RefundStatus.REJECTED;
    }

    public enum RefundStatus {
        PENDING, APPROVED, REJECTED
    }
}

