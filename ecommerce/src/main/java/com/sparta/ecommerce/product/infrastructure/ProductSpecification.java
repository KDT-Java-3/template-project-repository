package com.sparta.ecommerce.product.infrastructure;

import com.sparta.ecommerce.product.domain.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductSpecification {

    public static Specification<Product> withFilters(Long categoryId, String name,
                                                      BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            // COUNT 쿼리가 아닐 때만 fetch join 적용
            if (query.getResultType() == Product.class) {
                root.fetch("category", jakarta.persistence.criteria.JoinType.LEFT);
            }

            var predicates = new ArrayList<Predicate>();

            // 카테고리 필터
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            // 상품명 필터 (부분 검색)
            if (name != null && !name.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            // 최소 가격 필터
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            // 최대 가격 필터
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}