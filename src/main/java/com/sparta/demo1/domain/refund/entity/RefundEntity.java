package com.sparta.demo1.domain.refund.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import com.sparta.demo1.domain.purchase.entity.PurchaseEntity;
import com.sparta.demo1.domain.refund.enums.RefundStatus;
import com.sparta.demo1.domain.user.entity.UserEntity;
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

@Table(name = "refund")
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseEntity purchase;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    RefundStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public RefundEntity(
            UserEntity user,
            PurchaseEntity purchase,
            String reason,
            RefundStatus status
    ) {
        this.user = user;
        this.purchase = purchase;
        this.reason = reason;
        this.status = status;
    }

    public void updateStatus(RefundStatus status) {
        this.status = status;
    }
}