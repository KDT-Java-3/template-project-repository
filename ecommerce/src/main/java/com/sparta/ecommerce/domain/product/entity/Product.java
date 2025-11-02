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
import lombok.Generated;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private BigDecimal price;
    @Column(
            nullable = false
    )
    private Integer stock = 0;
    @ManyToOne
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;
    @Column
    private String description;

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

    @Generated
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Generated
    protected Product() {
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public BigDecimal getPrice() {
        return this.price;
    }

    @Generated
    public Integer getStock() {
        return this.stock;
    }

    @Generated
    public Category getCategory() {
        return this.category;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }

    @Generated
    public static class ProductBuilder {
        @Generated
        private String name;
        @Generated
        private BigDecimal price;
        @Generated
        private Integer stock;
        @Generated
        private Category category;
        @Generated
        private String description;

        @Generated
        ProductBuilder() {
        }

        @Generated
        public ProductBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public ProductBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        @Generated
        public ProductBuilder stock(final Integer stock) {
            this.stock = stock;
            return this;
        }

        @Generated
        public ProductBuilder category(final Category category) {
            this.category = category;
            return this;
        }

        @Generated
        public ProductBuilder description(final String description) {
            this.description = description;
            return this;
        }

        @Generated
        public Product build() {
            return new Product(this.name, this.price, this.stock, this.category, this.description);
        }

        @Generated
        public String toString() {
            String var10000 = this.name;
            return "Product.ProductBuilder(name=" + var10000 + ", price=" + String.valueOf(this.price) + ", stock=" + this.stock + ", category=" + String.valueOf(this.category) + ", description=" + this.description + ")";
        }
    }
}
