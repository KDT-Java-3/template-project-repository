package com.sparta.commerce.management.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Lob
    @Column(name = "description")
    String description;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    BigDecimal price;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "stock", nullable = false)
    Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @NotNull
    @CreationTimestamp
    @Column(name = "rg_dt", nullable = false)
    Instant rgDt;

    @NotNull
    @UpdateTimestamp
    @Column(name = "ud_dt", nullable = false)
    Instant udDt;

    @Builder
    public Product(String name, String description, BigDecimal price, Integer stock, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock != null ? stock : 0;
        this.category = category;
    }

    public void update(String name, String description, BigDecimal price, Integer stock, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public  void decreaseStock(Integer quantity) {
        if (this.stock < quantity) throw new IllegalArgumentException("재고 부족: 현재 " + this.stock + "개, 요청 " + quantity + "개");
        this.stock -= quantity;
    }

    public  void increaseStock(Integer quantity) {
        this.stock += quantity;
    }


}
