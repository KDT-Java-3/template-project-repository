package com.sparta.project.domain.purchase.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.project.common.enums.PurchaseStatus;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table
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

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PurchaseProduct> purchaseProducts = new ArrayList<>();


    @Builder
    public Purchase(
            User user,
            BigDecimal totalPrice,
            PurchaseStatus status
    ) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // 주문 취소
    public void cancel() {
        if (this.status != PurchaseStatus.PENDING) {
            throw new IllegalStateException("PENDING 상태의 주문만 취소할 수 있습니다. 현재 상태: " + this.status);
        }
        this.status = PurchaseStatus.CANCELED;
    }

    public void updateStatus(PurchaseStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("변경할 상태는 필수입니다.");
        }

        // 대기중인 주문만 변경 가능
        if (this.status != PurchaseStatus.PENDING) {
            throw new IllegalStateException("대기 상태의 주문만 변경이 가능합니다.");
        }

        this.status = newStatus;
    }

    // 주문 상품 추가
    public void addPurchaseProduct(PurchaseProduct purchaseProduct) {
        this.purchaseProducts.add(purchaseProduct);
    }

}