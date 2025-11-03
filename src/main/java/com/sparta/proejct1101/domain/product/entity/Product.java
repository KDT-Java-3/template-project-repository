package com.sparta.proejct1101.domain.product.entity;

import com.sparta.proejct1101.domain.category.entity.Category;
import com.sparta.proejct1101.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String prodName;
    private Integer price;
    private Integer stock;
    private String description;


    public void update(String prodName, Integer price, Integer stock, String description, Category category) {
        if (prodName != null) {
            this.prodName = prodName;
        }
        if (price != null) {
            this.price = price;
        }
        if (stock != null) {
            this.stock = stock;
        }
        if (description != null) {
            this.description = description;
        }
        if (category != null) {
            this.category = category;
        }
    }

    public void decreaseStock(Integer quantity) {
        if (this.stock < quantity) {
            throw new IllegalArgumentException("out of stock. 남은재고: " + this.stock );
        }
        this.stock -= quantity;
    }

}
