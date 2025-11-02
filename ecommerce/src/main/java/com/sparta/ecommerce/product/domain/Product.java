package com.sparta.ecommerce.product.domain;

import com.sparta.ecommerce.category.domain.Category;
import com.sparta.ecommerce.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("제품 일련번호")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @Comment("제품명")
    private String name;

    @Column(name = "description", length = 2000, nullable = false)
    @Comment("제품설명")
    private String description;

    @Column(name = "price", nullable = false)
    @Comment("가격")
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    @Comment("재고")
    private Integer stock;

    @Comment("카테고리 일련번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void update(String name, String description, BigDecimal price, Integer stock, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public void decreaseStock(Integer quantity) {
        if(this.stock - quantity < 0) throw new IllegalArgumentException("재고가 부족합니다.");
        this.stock -= quantity;
    }

    public void increaseStock(Integer quantity) {
        if(quantity < 1) throw new IllegalArgumentException("1 이상의 부터 증가가 가능합니다.");
        this.stock += quantity;
    }
}
