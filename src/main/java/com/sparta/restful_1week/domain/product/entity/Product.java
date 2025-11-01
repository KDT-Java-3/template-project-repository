package com.sparta.restful_1week.domain.product.entity;

import com.sparta.restful_1week.domain.category.dto.CategoryInDTO;
import com.sparta.restful_1week.domain.category.entity.Category;
import com.sparta.restful_1week.domain.product.dto.ProductInDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "Category")
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_At", nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_At", nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Builder
    public Product(ProductInDTO productInDTO) {
        this.name = productInDTO.getName();
        this.price = productInDTO.getPrice();
        this.stock = productInDTO.getStock();
        this.description = productInDTO.getDescription();
        this.createdAt = productInDTO.getCreatedAt();
        this.updatedAt = productInDTO.getUpdatedAt();
    }

    @Builder
    public void updateProduct(ProductInDTO productInDTO) {
        this.name = productInDTO.getName();
        this.price = productInDTO.getPrice();
        this.stock = productInDTO.getStock();
        this.description = productInDTO.getDescription();
        this.updatedAt = productInDTO.getUpdatedAt();
    }
}
