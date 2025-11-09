package com.jaehyuk.week_01_project.domain.product.repository;

import com.jaehyuk.week_01_project.domain.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * 상품 동적 쿼리를 위한 Specification 유틸리티 클래스
 */
public class ProductSpecification {

    /**
     * 카테고리 ID로 필터링
     */
    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    /**
     * 최소 가격으로 필터링 (minPrice 이상)
     */
    public static Specification<Product> hasMinPrice(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    /**
     * 최대 가격으로 필터링 (maxPrice 이하)
     */
    public static Specification<Product> hasMaxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (maxPrice == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }

    /**
     * 상품명 키워드로 검색 (LIKE %keyword%)
     */
    public static Specification<Product> hasNameContaining(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
        };
    }
}
