package com.sparta.proejct1101.domain.product.repository;

import com.sparta.proejct1101.domain.product.dto.request.ProductSearchReq;
import com.sparta.proejct1101.domain.product.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> searchWithFilters(ProductSearchReq searchReq) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 카테고리 필터
            if (searchReq.categoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), searchReq.categoryId()));
            }

            // 가격 범위 필터
            if (searchReq.minPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchReq.minPrice()));
            }
            if (searchReq.maxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchReq.maxPrice()));
            }

            // 상품명 키워드 검색
            if (searchReq.keyword() != null && !searchReq.keyword().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("prodName")),
                        "%" + searchReq.keyword().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}