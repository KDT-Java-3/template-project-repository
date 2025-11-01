package com.sparta.project.domain.refund.entity;

import com.sparta.project.common.enums.RefundStatus;
import com.sparta.project.domain.purchase.entity.Purchase;
import com.sparta.project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    Purchase purchase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    RefundStatus status;

    @Column(nullable = false, columnDefinition = "TEXT")
    String reason;

    @Column(columnDefinition = "TEXT")
    String rejectionReason; // 거절 사유 (관리자가 입력)

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public Refund(
            User user,
            Purchase purchase,
            RefundStatus status,
            String reason
    ) {
        this.user = user;
        this.purchase = purchase;
        this.status = status != null ? status : RefundStatus.PENDING;
        this.reason = reason;
    }

    /**
     * 환불 승인
     */
    public void approve() {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException(
                    "대기 중인 환불만 승인할 수 있습니다. 현재 상태: " + this.status.getDescription()
            );
        }
        this.status = RefundStatus.APPROVED;
    }

    /**
     * 환불 거절
     * @param rejectionReason 거절 사유
     */
    public void reject(String rejectionReason) {
        if (this.status != RefundStatus.PENDING) {
            throw new IllegalStateException(
                    "대기 중인 환불만 거절할 수 있습니다. 현재 상태: " + this.status.getDescription()
            );
        }
        this.status = RefundStatus.REJECTED;
        this.rejectionReason = rejectionReason;
    }

    /**
     * 환불 가능 여부 확인
     * @return 승인/거절 가능하면 true
     */
    public boolean isProcessable() {
        return this.status == RefundStatus.PENDING;
    }
}