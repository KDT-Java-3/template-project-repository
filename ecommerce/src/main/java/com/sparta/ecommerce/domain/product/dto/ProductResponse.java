package com.sparta.ecommerce.domain.product.dto;

import com.sparta.ecommerce.domain.product.entity.Product;
import java.math.BigDecimal;
import lombok.Generated;

public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long category_id;
    private String category_name;
    private String description;

    public static ProductResponse fromEntity(Product product) {
        return builder().id(product.getId()).name(product.getName()).price(product.getPrice()).stock(product.getStock()).category_id(product.getCategory().getId()).category_name(product.getCategory().getName()).description(product.getDescription()).build();
    }

    @Generated
    public static ProductResponseBuilder builder() {
        return new ProductResponseBuilder();
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
    public BigDecimal getPrice() {
        return this.price;
    }

    @Generated
    public Integer getStock() {
        return this.stock;
    }

    @Generated
    public Long getCategory_id() {
        return this.category_id;
    }

    @Generated
    public String getCategory_name() {
        return this.category_name;
    }

    @Generated
    public String getDescription() {
        return this.description;
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
    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    @Generated
    public void setStock(final Integer stock) {
        this.stock = stock;
    }

    @Generated
    public void setCategory_id(final Long category_id) {
        this.category_id = category_id;
    }

    @Generated
    public void setCategory_name(final String category_name) {
        this.category_name = category_name;
    }

    @Generated
    public void setDescription(final String description) {
        this.description = description;
    }

    @Generated
    public ProductResponse(final Long id, final String name, final BigDecimal price, final Integer stock, final Long category_id, final String category_name, final String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category_id = category_id;
        this.category_name = category_name;
        this.description = description;
    }

    @Generated
    public static class ProductResponseBuilder {
        @Generated
        private Long id;
        @Generated
        private String name;
        @Generated
        private BigDecimal price;
        @Generated
        private Integer stock;
        @Generated
        private Long category_id;
        @Generated
        private String category_name;
        @Generated
        private String description;

        @Generated
        ProductResponseBuilder() {
        }

        @Generated
        public ProductResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @Generated
        public ProductResponseBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public ProductResponseBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        @Generated
        public ProductResponseBuilder stock(final Integer stock) {
            this.stock = stock;
            return this;
        }

        @Generated
        public ProductResponseBuilder category_id(final Long category_id) {
            this.category_id = category_id;
            return this;
        }

        @Generated
        public ProductResponseBuilder category_name(final String category_name) {
            this.category_name = category_name;
            return this;
        }

        @Generated
        public ProductResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        @Generated
        public ProductResponse build() {
            return new ProductResponse(this.id, this.name, this.price, this.stock, this.category_id, this.category_name, this.description);
        }

        @Generated
        public String toString() {
            Long var10000 = this.id;
            return "ProductResponse.ProductResponseBuilder(id=" + var10000 + ", name=" + this.name + ", price=" + String.valueOf(this.price) + ", stock=" + this.stock + ", category_id=" + this.category_id + ", category_name=" + this.category_name + ", description=" + this.description + ")";
        }
    }
}
