package com.sparta.project1.domain.refund.entity;


import com.sparta.project1.domain.BaseEntity;
import com.sparta.project1.domain.order.domain.Orders;
import com.sparta.project1.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "refund")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Enumerated(value = EnumType.STRING)
    private RefundStatus status;

    @Column(columnDefinition = "mediumtext")
    private String reason;

    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;

    public static Refund register(Orders orders, Users users, String reason) {
        return new Refund(orders, users, reason);
    }

    Refund(Orders orders, Users users, String reason) {
        this.orders = orders;
        this.users = users;
        this.status = RefundStatus.PENDING;
        this.reason = reason;
    }

    public void changeStatus(String status) {
        RefundStatus refundStatus = RefundStatus.findByValue(status);
        this.status = refundStatus;
        if (refundStatus == RefundStatus.APPROVED) {
            this.approvedAt = LocalDateTime.now();
        } else if (refundStatus == RefundStatus.REJECTED) {
            this.rejectedAt = LocalDateTime.now();
        }
    }
}
