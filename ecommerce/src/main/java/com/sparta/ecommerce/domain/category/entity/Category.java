package com.sparta.ecommerce.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column
    private String description;

    public void update(String name, String description) {
        if (name != null) {
            this.name = name;
        }

        if (name != null) {
            this.description = description;
        }
    }

    @Builder
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
