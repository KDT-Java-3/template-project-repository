package com.sprata.sparta_ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
public class Category extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCategory")
    private List<Category> subCategories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @Builder
    public Category(String name, String description, Category parentCategory) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        if(this.parentCategory != null){
            this.parentCategory.getSubCategories().add(this);
        }
    }

    public void update(@NotBlank String name, String description,  Category parentCategory) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        if(this.parentCategory != null){
            this.parentCategory.getSubCategories().add(this);
        }
    }
}
