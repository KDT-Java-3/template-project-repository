package com.pepponechoi.project.domain.product.entity;

import com.pepponechoi.project.domain.category.entity.Category;
import com.pepponechoi.project.domain.product.dto.request.ProductUpdateRequest;
import com.pepponechoi.project.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column
    private Long stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @Builder
    public Product(String name, Long price, Long stock, Category category, User user) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.user = user;
        category.addProduct(this);
        user.addProduct(this);
    }

    public void update(ProductUpdateRequest request, Category category, User user) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.stock = request.getStock();
        if (category != null) {
            category.removeProduct(this);
            this.category = category;
            category.addProduct(this);
        }
        if (user != null) {
            user.removeProduct(this);
            this.user = user;
            user.addProduct(this);
        }
    }
}
