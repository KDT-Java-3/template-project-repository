package com.sparta.jc.domain.product.entity;

import com.sparta.jc.domain.category.entity.Category;
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
public class Product {

    /**
     * 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 카테고리 객체
     * 다대일 관계: 하나의 카테고리에 여러 상품이 속할 수 있음
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    /**
     * 상품명
     */
    @Column(nullable = false, unique = true)
    private String name;
    /**
     * 상품 설명
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    /**
     * 상품 가격
     */
    @Column(nullable = false)
    private BigDecimal price;
    /**
     * 재고 수량
     */
    @Column(nullable = false)
    private Integer stock;
    /**
     * 생성일
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    /**
     * 수정일
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Product(
            Category category,
            String name,
            String description,
            BigDecimal price,
            Integer stock
    ) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // ==========================================================================================

//    public void decreaseStock(int quantity) {
//        if (quantity <= 0) {
//            throw new IllegalArgumentException("quantity must be greater than zero");
//        }
//        if (this.stock < quantity) {
//            throw new IllegalStateException("not enough stock");
//        }
//        this.stock -= quantity;
//    }
//
//    public void increaseStock(int quantity) {
//        if (quantity <= 0) {
//            throw new IllegalArgumentException("quantity must be greater than zero");
//        }
//        this.stock += quantity;
//    }

    public void updateDetails(
            Category category,
            String name,
            String description,
            BigDecimal price,
            Integer stock
    ) {
        if (category == null) {
            throw new IllegalArgumentException("category must not be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price must be positive");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("stock must be zero or greater");
        }
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

}
