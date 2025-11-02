package com.sparta.ecommerce.domain.category.dto;

import lombok.Generated;

public class CategoryCreateRequest {
    private String name;
    private String description;

    @Generated
    public static CategoryCreateRequestBuilder builder() {
        return new CategoryCreateRequestBuilder();
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
    public CategoryCreateRequest() {
    }

    @Generated
    public CategoryCreateRequest(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    @Generated
    public static class CategoryCreateRequestBuilder {
        @Generated
        private String name;
        @Generated
        private String description;

        @Generated
        CategoryCreateRequestBuilder() {
        }

        @Generated
        public CategoryCreateRequestBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public CategoryCreateRequestBuilder description(final String description) {
            this.description = description;
            return this;
        }

        @Generated
        public CategoryCreateRequest build() {
            return new CategoryCreateRequest(this.name, this.description);
        }

        @Generated
        public String toString() {
            return "CategoryCreateRequest.CategoryCreateRequestBuilder(name=" + this.name + ", description=" + this.description + ")";
        }
    }
}
