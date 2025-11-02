package com.sparta.demo1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    Purchase purchase;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(nullable = false)
    Integer quantity;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

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