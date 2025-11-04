package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User와 1:N 양방향 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status = PurchaseStatus.PENDING;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder
    public Purchase(User user, BigDecimal totalPrice, String shippingAddress, PurchaseStatus status) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
        this.status = status != null ? status : PurchaseStatus.PENDING;
    }

    // 연관관계 편의 메서드
    public void addPurchaseItem(PurchaseItem purchaseItem) {
        purchaseItems.add(purchaseItem);
        purchaseItem.setPurchase(this);
    }

    // 상태 변경 메서드
    public void updateStatus(PurchaseStatus newStatus) {
        this.status = newStatus;
    }

    public void updateTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public enum PurchaseStatus {
        PENDING, COMPLETED, CANCELLED
    }
}

