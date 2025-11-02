package com.sparta.ecommerce.refund.domain;

import com.sparta.ecommerce.common.domain.BaseEntity;
import com.sparta.ecommerce.purchase.domain.Purchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Table(name = "refund")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("환불 일련번호")
    private Long id;

    @Column(name = "user_id")
    @Comment("유저 일련번호")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    @Comment("주문 일련번호")
    private Purchase purchase;

    @Column(name = "reason", length = 2000)
    @Comment("환불 사유")
    private String reason;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private RefundStatus status;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    public void changeStatus(RefundStatus status) {
        if (this.status == status) {
            throw new IllegalArgumentException("이미 같은 상태입니다.");
        }

        if (this.status != RefundStatus.PENDING) {
            throw new IllegalArgumentException("PENDING 상태일 때만 상태 변경이 가능합니다.");
        }

        this.status = status;

        if (status == RefundStatus.APPROVED) {
            this.approvedAt = LocalDateTime.now();
        } else if (status == RefundStatus.REJECTED) {
            this.rejectedAt = LocalDateTime.now();
        }
    }
}
