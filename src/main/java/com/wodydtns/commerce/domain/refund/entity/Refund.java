package com.wodydtns.commerce.domain.refund.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.wodydtns.commerce.domain.member.entity.Member;
import com.wodydtns.commerce.domain.order.entity.Order;
import com.wodydtns.commerce.global.enums.RefundStatus;

@Entity
@Table(name = "refunds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Member member;

    @OneToOne
    @NotNull
    private Order order;

    @NotBlank
    private String reason;

    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus = RefundStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Refund(Long id, Member member, Order order, String reason, RefundStatus refundStatus) {
        this.member = member;
        this.order = order;
        this.reason = reason;
        this.refundStatus = refundStatus;
    }

    public void updateRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

}
