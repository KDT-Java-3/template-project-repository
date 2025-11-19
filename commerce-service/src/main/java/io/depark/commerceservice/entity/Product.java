package io.depark.commerceservice.entity;

import io.depark.commerceservice.common.YNToBooleanConverter;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(uniqueConstraints = @UniqueConstraint(name = "uk_product_name", columnNames = "name"))
@SQLDelete(sql = "UPDATE product SET del_yn = 'Y' WHERE id = ?")
@SQLRestriction("del_yn = 'N'")
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @Column(nullable = false)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    Integer stock;

    @Column(name = "del_yn", columnDefinition = "CHAR(1)", nullable = false)
    @Convert(converter = YNToBooleanConverter.class)
    Boolean isDeleted;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

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

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        if (this.stock < quantity) {
            throw new IllegalStateException("not enough stock");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        this.stock += quantity;
    }

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
