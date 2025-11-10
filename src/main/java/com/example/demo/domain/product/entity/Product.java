package com.example.demo.domain.product.entity;

import com.example.demo.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "products",
    uniqueConstraints = @UniqueConstraint(name = "uk_product_name", columnNames = "name")
)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Builder
    public Product(String name, String description, BigDecimal price, Integer stock, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock != null ? stock : 0;
        this.categoryId = categoryId;
    }

    /**
     * 상품 정보 수정
     */
    public void update(String name, String description, BigDecimal price, Integer stock, Long categoryId) {
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
        if (categoryId != null) {
            this.categoryId = categoryId;
        }
    }

    /**
     * 재고 감소
     */
    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    /**
     * 재고 증가
     */
    public void increaseStock(int quantity) {
        this.stock += quantity;
    }
}
