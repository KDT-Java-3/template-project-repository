package com.sparta.hodolee246.week01project.entity;

import com.sparta.hodolee246.week01project.service.request.ProductDto;
import com.sparta.hodolee246.week01project.service.response.ProductInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateProduct(ProductDto dto, Category category) {
        this.name = dto.name();
        this.description = dto.description();
        this.price = dto.price();
        this.stock = dto.stock();
        this.category = category;
    }

    public void decreaseStock(int quantity) {
        if (this.stock - quantity < 0) {
            throw new RuntimeException("no enough stock");
        }
        this.stock -= quantity;
    }

    public ProductInfo toResponse() {
        return new ProductInfo(
                this.id,
                this.name,
                this.description,
                this.price,
                this.stock,
                this.category.getId(),
                this.category.getName()
        );
    }

    @Builder
    public Product(Long id, String name, String description, BigDecimal price, Integer stock, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
}