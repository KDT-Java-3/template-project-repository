package io.depark.commerceservice.entity;

import io.depark.commerceservice.entity.enums.PurchaseStatus;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.PERSIST)
    List<PurchaseProduct> purchaseProducts = new ArrayList<>();

    @Column(nullable = false, precision = 18, scale = 2)
    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    PurchaseStatus status;

    @Column(columnDefinition = "TEXT")
    String shippingAddress;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public void addPurchaseProduct(PurchaseProduct purchaseProduct) {
        this.purchaseProducts.add(purchaseProduct);
        purchaseProduct.updatePurchase(this);
    }

    public void cancel() {
        if (this.status != PurchaseStatus.PENDING) {
            throw new IllegalArgumentException("status must be PENDING");
        } else if (this.status == PurchaseStatus.CANCELED) {
            throw new IllegalArgumentException("purchaseStatus is already cancelled");
        }
        this.status = PurchaseStatus.CANCELED;
        this.purchaseProducts.forEach(PurchaseProduct::restoreProductStock);
    }

    public void refund() {
        if (this.status == PurchaseStatus.REFUNDED) {
            throw new IllegalArgumentException("purchaseStatus is already refunded");
        }
        this.status = PurchaseStatus.REFUNDED;
        this.purchaseProducts.forEach(PurchaseProduct::restoreProductStock);
    }

    @Builder
    public Purchase(
            User user,
            BigDecimal totalPrice,
            PurchaseStatus status,
            String shippingAddress
    ) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public void markCompleted() {
        this.status = PurchaseStatus.COMPLETED;
    }

    public void markRefunded() {
        this.status = PurchaseStatus.REFUNDED;
    }
}
