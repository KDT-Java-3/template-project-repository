package com.spartaecommerce.product.infrastructure.persistence.jpa.entity;

import com.spartaecommerce.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product")
public class ProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public static ProductJpaEntity from(Product product) {
        return new ProductJpaEntity(
            null,
            product.getName(),
            product.getPrice().toString(),
            product.getStock(),
            product.getCategoryId(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}