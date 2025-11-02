package com.sparta.ecommerce.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Generated;

@Entity
public class Category {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            nullable = false,
            length = 255
    )
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

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Generated
    public static CategoryBuilder builder() {
        return new CategoryBuilder();
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
    public String getDescription() {
        return this.description;
    }

    @Generated
    protected Category() {
    }

    @Generated
    public static class CategoryBuilder {
        @Generated
        private String name;
        @Generated
        private String description;

        @Generated
        CategoryBuilder() {
        }

        @Generated
        public CategoryBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public CategoryBuilder description(final String description) {
            this.description = description;
            return this;
        }

        @Generated
        public Category build() {
            return new Category(this.name, this.description);
        }

        @Generated
        public String toString() {
            return "Category.CategoryBuilder(name=" + this.name + ", description=" + this.description + ")";
        }
    }
}
