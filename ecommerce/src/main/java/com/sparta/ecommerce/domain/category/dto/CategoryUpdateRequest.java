package com.sparta.ecommerce.domain.category.dto;

import lombok.Generated;

public class CategoryUpdateRequest {
    private Long id;
    private String name;
    private String description;

    @Generated
    public static CategoryUpdateRequestBuilder builder() {
        return new CategoryUpdateRequestBuilder();
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
    public CategoryUpdateRequest() {
    }

    @Generated
    public CategoryUpdateRequest(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public void setDescription(final String description) {
        this.description = description;
    }

    @Generated
    public static class CategoryUpdateRequestBuilder {
        @Generated
        private Long id;
        @Generated
        private String name;
        @Generated
        private String description;

        @Generated
        CategoryUpdateRequestBuilder() {
        }

        @Generated
        public CategoryUpdateRequestBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public CategoryUpdateRequestBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public CategoryUpdateRequestBuilder description(final String description) {
            this.description = description;
            return this;
        }

        @Generated
        public CategoryUpdateRequest build() {
            return new CategoryUpdateRequest(this.id, this.name, this.description);
        }

        @Generated
        public String toString() {
            return "CategoryUpdateRequest.CategoryUpdateRequestBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ")";
        }
    }
}
