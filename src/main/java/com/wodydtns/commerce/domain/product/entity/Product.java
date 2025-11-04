package com.wodydtns.commerce.domain.product.entity;

import java.time.LocalDateTime;

import com.wodydtns.commerce.domain.category.entity.Category;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Valid
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotNull
    private int price;

    @Column(nullable = false)
    @NotNull
    private int stock;

    @Column
    private String description;

    @Column(name = "category_id", nullable = false)
    @NotNull
    private Long categoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Product(Long id, String name, Integer price, Integer stock, String description, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Stock management methods
    public void decreaseStock() {
        this.stock--;
    }

    public void increaseStock() {
        this.stock++;
    }

    public boolean hasStock() {
        return this.stock > 0;
    }
}
