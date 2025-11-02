package com.sparta.ecommerce.domain.category.dto;

import com.sparta.ecommerce.domain.category.entity.Category;
import lombok.Generated;

public class CategoryResponse {
    private Long id;
    private String name;
    private String description;

    public static CategoryResponse fromEntity(Category category) {
        return builder().id(category.getId()).name(category.getName()).description(category.getDescription()).build();
    }

    @Generated
    public static CategoryResponseBuilder builder() {
        return new CategoryResponseBuilder();
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
    public CategoryResponse() {
    }

    @Generated
    public CategoryResponse(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Generated
    public static class CategoryResponseBuilder {
        @Generated
        private Long id;
        @Generated
        private String name;
        @Generated
        private String description;

        @Generated
        CategoryResponseBuilder() {
        }

        @Generated
        public CategoryResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public CategoryResponseBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public CategoryResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        @Generated
        public CategoryResponse build() {
            return new CategoryResponse(this.id, this.name, this.description);
        }

        @Generated
        public String toString() {
            return "CategoryResponse.CategoryResponseBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ")";
        }
    }
}
