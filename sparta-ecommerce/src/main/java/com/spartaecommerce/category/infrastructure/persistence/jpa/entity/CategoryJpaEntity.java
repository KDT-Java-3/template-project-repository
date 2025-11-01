package com.spartaecommerce.category.infrastructure.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spartaecommerce.category.domain.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "category")
public class CategoryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryJpaEntity parent;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static CategoryJpaEntity from(Category category) {
        return new CategoryJpaEntity(
            category.getId(),
            category.getName(),
            null,
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }

    public CategoryJpaEntity create(
        String name,
        CategoryJpaEntity parent
    ) {
        return new CategoryJpaEntity(name, parent);
    }

    private CategoryJpaEntity(
        String name,
        CategoryJpaEntity parent
    ) {
        this.name = name;
        this.parent = parent;
    }
}
