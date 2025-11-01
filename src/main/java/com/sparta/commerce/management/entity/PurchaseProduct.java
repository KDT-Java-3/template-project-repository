package com.sparta.commerce.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;

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
    @Column(name = "id", nullable = false, length = 16)
    String id;

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
    Instant rgDt;

    @NotNull
    @CreationTimestamp
    @UpdateTimestamp
    @Column(name = "ud_dt", nullable = false)
    Instant udDt;

    @Builder
    public PurchaseProduct(Purchase purchase, Product product, Integer quantity, BigDecimal price) {
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

}
