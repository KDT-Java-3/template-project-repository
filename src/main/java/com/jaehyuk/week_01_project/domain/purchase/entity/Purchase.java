package com.jaehyuk.week_01_project.domain.purchase.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jaehyuk.week_01_project.domain.purchase.enums.PurchaseStatus;
import com.jaehyuk.week_01_project.domain.user.entity.User;
import com.jaehyuk.week_01_project.exception.custom.InvalidStatusTransitionException;
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
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "purchase")
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    PurchaseStatus status;

    @Column(nullable = false)
    String shippingAddress;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public Purchase(User user, BigDecimal totalPrice, PurchaseStatus status, String shippingAddress) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public void updateStatus(PurchaseStatus newStatus) {
        // 상태 전이 규칙 검증
        if (this.status == PurchaseStatus.PENDING) {
            // PENDING → COMPLETED, CANCELED, REFUND_REQUESTED
            if (newStatus != PurchaseStatus.COMPLETED &&
                newStatus != PurchaseStatus.CANCELED &&
                newStatus != PurchaseStatus.REFUND_REQUESTED) {
                throw new InvalidStatusTransitionException(
                        String.format("PENDING 상태에서는 COMPLETED, CANCELED, REFUND_REQUESTED로만 변경 가능합니다. 요청 상태: %s",
                                newStatus)
                );
            }
        } else if (this.status == PurchaseStatus.REFUND_REQUESTED) {
            // REFUND_REQUESTED → REFUNDED, PENDING
            if (newStatus != PurchaseStatus.REFUNDED &&
                newStatus != PurchaseStatus.PENDING) {
                throw new InvalidStatusTransitionException(
                        String.format("REFUND_REQUESTED 상태에서는 REFUNDED, PENDING으로만 변경 가능합니다. 요청 상태: %s",
                                newStatus)
                );
            }
        } else {
            // 그 외 상태에서는 변경 불가
            throw new InvalidStatusTransitionException(
                    String.format("주문 상태 변경 불가능합니다. 현재 상태: %s", this.status)
            );
        }

        this.status = newStatus;
    }
}
