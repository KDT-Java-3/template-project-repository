package com.sparta.commerce.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
import java.util.UUID;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "PurchaseProduct")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseProduct {

    @Id
    @Size(max = 16)
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    Purchase purchase;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    BigDecimal price; //주문 당시 가격

    @NotNull
    @CreationTimestamp
    @Column(name = "rg_dt", nullable = false)
    LocalDateTime rgDt;

    @NotNull
    @CreationTimestamp
    @UpdateTimestamp
    @Column(name = "ud_dt", nullable = false)
    LocalDateTime udDt;

    @Builder
    public PurchaseProduct(Purchase purchase, Product product, Integer quantity, BigDecimal price) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

}
