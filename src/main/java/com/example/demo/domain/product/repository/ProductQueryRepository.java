package com.example.demo.domain.product.repository;

import com.example.demo.domain.product.dto.request.ProductSearchCondition;
import com.example.demo.domain.product.dto.response.ProductSummary;
import com.example.demo.domain.product.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;

    /**
     * 상품 조건부 검색 + 페이징 + 동적 정렬
     */
    public Page<ProductSummary> search(ProductSearchCondition condition, Pageable pageable) {
        // 동적 조건 생성
        BooleanBuilder where = buildConditions(condition);

        // 동적 정렬 생성
        List<OrderSpecifier<?>> orderSpecifiers = buildOrderSpecifiers(pageable);

        // 데이터 조회
        List<ProductSummary> content = queryFactory
            .select(Projections.constructor(ProductSummary.class,
                product.id,
                product.name,
                product.price,
                product.stock,
                product.createdAt))
            .from(product)
            .where(where)
            .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 전체 카운트 조회
        Long total = queryFactory
            .select(product.count())
            .from(product)
            .where(where)
            .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    /**
     * 동적 조건 생성
     */
    private BooleanBuilder buildConditions(ProductSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        // 카테고리 필터
        if (condition.getCategoryId() != null) {
            builder.and(product.categoryId.eq(condition.getCategoryId()));
        }

        // 최소 가격
        if (condition.getPriceMin() != null) {
            builder.and(product.price.goe(condition.getPriceMin()));
        }

        // 최대 가격
        if (condition.getPriceMax() != null) {
            builder.and(product.price.loe(condition.getPriceMax()));
        }

        // 재고 상태 필터 (includeZeroStock이 false면 재고 있는 것만)
        if (condition.getIncludeZeroStock() != null && !condition.getIncludeZeroStock()) {
            builder.and(product.stock.gt(0));
        }

        // 상품명 키워드 검색
        if (condition.getNameKeyword() != null && !condition.getNameKeyword().trim().isEmpty()) {
            builder.and(product.name.containsIgnoreCase(condition.getNameKeyword().trim()));
        }

        return builder;
    }

    /**
     * 동적 정렬 생성
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<OrderSpecifier<?>> buildOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        pageable.getSort().forEach(order -> {
            Expression target = switch (order.getProperty()) {
                case "price" -> product.price;
                case "createdAt" -> product.createdAt;
                case "stock" -> product.stock;
                case "name" -> product.name;
                default -> product.id;
            };
            orderSpecifiers.add(
                order.isAscending()
                    ? new OrderSpecifier(Order.ASC, target)
                    : new OrderSpecifier(Order.DESC, target)
            );
        });

        return orderSpecifiers;
    }
}
