package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.repository.projection.ProductSummaryDto;
import com.example.demo.repository.projection.QProductSummaryDto;
import com.example.demo.service.dto.ProductSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.entity.QCategory.category;
import static com.example.demo.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Product> findByCondition(ProductSearchCondition condition) {
        return queryFactory
                .selectFrom(product)
                .leftJoin(product.category, category).fetchJoin()
                .where(buildConditions(condition))
                .orderBy(product.createdAt.desc())
                .fetch();
    }

    public Page<Product> findPageByCondition(ProductSearchCondition condition, Pageable pageable) {
        List<Product> content = queryFactory
                .selectFrom(product)
                .leftJoin(product.category, category).fetchJoin()
                .where(buildConditions(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(product.count())
                .from(product)
                .where(buildConditions(condition))
                .fetchOne();

        long totalCount = total != null ? total : 0L;
        return new PageImpl<>(content, pageable, totalCount);
    }

    public List<ProductSummaryDto> findSummariesByCategoryId(Long categoryId) {
        return queryFactory
                .select(new QProductSummaryDto(
                        product.id,
                        product.name,
                        product.category.name,
                        product.price,
                        product.stock,
                        product.createdAt
                ))
                .from(product)
                .join(product.category, category)
                .where(categoryEquals(categoryId))
                .orderBy(product.createdAt.desc())
                .fetch();
    }

    private BooleanExpression[] buildConditions(ProductSearchCondition condition) {
        if (condition == null) {
            return new BooleanExpression[0];
        }
        return new BooleanExpression[]{
                nameContains(condition.getName()),
                priceGoe(condition.getMinPrice()),
                priceLoe(condition.getMaxPrice()),
                categoryEquals(condition.getCategoryId()),
                stockGoe(condition.getMinStock()),
                stockLoe(condition.getMaxStock())
        };
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? product.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression priceGoe(BigDecimal minPrice) {
        return minPrice != null ? product.price.goe(minPrice) : null;
    }

    private BooleanExpression priceLoe(BigDecimal maxPrice) {
        return maxPrice != null ? product.price.loe(maxPrice) : null;
    }

    private BooleanExpression categoryEquals(Long categoryId) {
        return categoryId != null ? product.category.id.eq(categoryId) : null;
    }

    private BooleanExpression stockGoe(Integer minStock) {
        return minStock != null ? product.stock.goe(minStock) : null;
    }

    private BooleanExpression stockLoe(Integer maxStock) {
        return maxStock != null ? product.stock.loe(maxStock) : null;
    }
}
