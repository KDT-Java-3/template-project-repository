package com.sparta.templateprojectrepository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String productName;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private int stock;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @Builder
    public Product(Long id,
                   String productName,
                   BigDecimal price,
                   int stock,
                   String description,
                   Category category
    ) {
        this.id = id;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
    
    // 재고 수량 감소
    public void decreseStock(int orderQuantity) {
        if(this.stock < orderQuantity) {
            throw new IllegalArgumentException("재고 수량 부족");
        }

        this.stock -= orderQuantity;

    }
}
