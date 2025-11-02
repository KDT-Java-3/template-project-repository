package com.example.project_01.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

// CategoryEntity에 추가
import java.util.List;

@Entity(name = "CATEGORY")
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", updatable = false, nullable = false)
    Integer seq;

    @Column(name = "CATEGORY_NAME", nullable = false, length = 30)
    String categoryName;

    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<ProductEntity> products;
}