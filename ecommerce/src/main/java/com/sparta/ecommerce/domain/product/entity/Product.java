package com.sparta.ecommerce.domain.product.entity;

import com.sparta.ecommerce.domain.category.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stock = 0;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column
    private String description;

    @Builder
    public Product(String name, BigDecimal price, Integer stock, Category category, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.description = description;
    }

    public void update(String name, String description, BigDecimal price, Integer stock, Category category) {
        if (name != null) {
            this.name = name;
        }

        if (description != null) {
            this.description = description;
        }

        if (price != null) {
            this.price = price;
        }

        if (stock != null) {
            this.stock = stock;
        }

        if (category != null) {
            this.category = category;
        }

    }

    public void decrease(int use) {
        if (this.stock >= use) {
            this.stock = this.stock - use;
        } else {
            throw new IllegalStateException("재고가 부족합니다. (현재 재고: " + this.stock + ")");
        }
    }

    public void increase(int amount) {
        this.stock = this.stock + amount;
    }
}
