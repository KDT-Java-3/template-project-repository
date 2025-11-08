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
    @Column(nullable = false)
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
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    /**
     * 수정일
     */
    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

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
}
