package io.depark.commerceservice.entity;

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

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    BigDecimal price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public void updatePurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public BigDecimal calculateToTalPrice() {
        return price.multiply(new BigDecimal(quantity));
    }

    public void restoreProductStock() {
        this.product.increaseStock(quantity);
    }

    @Builder
    public PurchaseProduct(
            Purchase purchase,
            Product product,
            Integer quantity,
            BigDecimal price
    ) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}