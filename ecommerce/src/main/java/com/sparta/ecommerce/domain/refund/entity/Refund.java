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
import lombok.Generated;

@Entity
public class Refund {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "order_id",
            nullable = false
    )
    private Order order;
    @Column(
            nullable = false
    )
    private String reason;
    @Column(
            name = "refund_date"
    )
    private LocalDateTime refundDate = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false
    )
    private RefundStatus status;

    public Refund(User user, Order order, String reason) {
        this.status = RefundStatus.pending;
        this.user = user;
        this.order = order;
        this.reason = reason;
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

    @Generated
    public static RefundBuilder builder() {
        return new RefundBuilder();
    }

    @Generated
    protected Refund() {
        this.status = RefundStatus.pending;
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public User getUser() {
        return this.user;
    }

    @Generated
    public Order getOrder() {
        return this.order;
    }

    @Generated
    public String getReason() {
        return this.reason;
    }

    @Generated
    public LocalDateTime getRefundDate() {
        return this.refundDate;
    }

    @Generated
    public RefundStatus getStatus() {
        return this.status;
    }

    @Generated
    public static class RefundBuilder {
        @Generated
        private User user;
        @Generated
        private Order order;
        @Generated
        private String reason;

        @Generated
        RefundBuilder() {
        }

        @Generated
        public RefundBuilder user(final User user) {
            this.user = user;
            return this;
        }

        @Generated
        public RefundBuilder order(final Order order) {
            this.order = order;
            return this;
        }

        @Generated
        public RefundBuilder reason(final String reason) {
            this.reason = reason;
            return this;
        }

        @Generated
        public Refund build() {
            return new Refund(this.user, this.order, this.reason);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.user);
            return "Refund.RefundBuilder(user=" + var10000 + ", order=" + String.valueOf(this.order) + ", reason=" + this.reason + ")";
        }
    }
}

