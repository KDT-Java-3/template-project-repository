package com.sparta.demo1.domain.product.repository;

import com.sparta.demo1.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProductSpecification {

    public static Specification<ProductEntity> categoryIn(List<Long> categoryIds) {
        return (root, query, cb) ->
                categoryIds == null || categoryIds.isEmpty() ? null :
                        root.get("category").get("id").in(categoryIds);
    }

    public static Specification<ProductEntity> minPrice(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? null : cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<ProductEntity> maxPrice(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? null : cb.lessThanOrEqualTo(root.get("price"), max);
    }

    public static Specification<ProductEntity> nameContains(String keyword) {
        return (root, query, cb) ->
                keyword == null || keyword.isBlank() ? null :
                        cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }
}