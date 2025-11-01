package com.sparta.bootcamp.java_2_example.domain.refund.entity;

import com.sparta.bootcamp.java_2_example.common.enums.ErrorCode;
import com.sparta.bootcamp.java_2_example.common.enums.RefundStatus;
import com.sparta.bootcamp.java_2_example.domain.order.entity.Order;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;
import com.sparta.bootcamp.java_2_example.exception.CustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RefundStatus status = RefundStatus.PENDING;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal refundAmount;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Refund(Order order, User user, String reason) {
        this.order = order;
        this.user = user;
        this.reason = reason;
        this.refundAmount = order.getTotalAmount();
    }

    public void approve() {
        if (this.status != RefundStatus.PENDING) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "대기 중인 환불 요청만 승인할 수 있습니다.");
        }
        this.status = RefundStatus.APPROVED;
    }

    public void reject() {
        if (this.status != RefundStatus.PENDING) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "대기 중인 환불 요청만 거절할 수 있습니다.");
        }
        this.status = RefundStatus.REJECTED;
    }
}
