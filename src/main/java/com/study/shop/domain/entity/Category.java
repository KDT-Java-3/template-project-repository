package com.study.shop.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "category")
public class Category {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @Builder
    public Category(
            String name,
            Category parent,
            String description
    ) {
        this.name = name;
        this.parent = parent;
        this.description = description;
    }
}
