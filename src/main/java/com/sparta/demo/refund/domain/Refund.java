package com.sparta.demo.refund.domain;

import com.sparta.demo.order.domain.Order;
import com.sparta.demo.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class Refund {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(value = NO_CONSTRAINT))
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(value = NO_CONSTRAINT))
    private Order order;

    @Column(nullable = false)
    private String reason;

    @Builder.Default
    @Enumerated(STRING)
    @Column(nullable = false)
    private RefundStatus status = RefundStatus.PENDING;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime refundDateTime;

    public static Refund create(User user, Order order, String reason) {
        return Refund.builder()
                .user(user)
                .order(order)
                .reason(reason)
                .build();
    }

    public void approve() {
        if (this.status.cantApprove()) {
            throw new IllegalStateException("대기 중인 환불 요청만 승인할 수 있습니다.");
        }
        this.status = RefundStatus.APPROVED;
        this.order.refund();
    }

    public void reject() {
        if (this.status.cantReject()) {
            throw new IllegalStateException("대기 중인 환불 요청만 거절할 수 있습니다.");
        }
        this.status = RefundStatus.REJECTED;
    }
}
