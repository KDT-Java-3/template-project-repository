package com.example.week01_project.repository;
import com.example.week01_project.domain.category.QCategory;
import com.example.week01_project.domain.product.Product;
import com.example.week01_project.domain.product.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory query;
    public ProductQueryRepositoryImpl(JPAQueryFactory query) { this.query = query; }

    @Override
    public Page<Product> search(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
                                Boolean includeZeroStock, Pageable pageable) {
        QProduct p = QProduct.product;
        QCategory c = QCategory.category;

        var base = query.selectFrom(p).leftJoin(p.category, c).fetchJoin()
                .where(
                        eqCategoryId(categoryId),
                        goePrice(minPrice),
                        loePrice(maxPrice),
                        stockPredicate(includeZeroStock)
                );

        long total = base.fetch().size(); // 간단 집계 (대용량이면 count 쿼리 분리 권장)

        List<Product> content = base
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QuerydslSortUtil.toOrders(pageable.getSort(), p)) // 아래 유틸 참고
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression eqCategoryId(Long categoryId){
        return categoryId == null ? null : QProduct.product.category.id.eq(categoryId);
    }
    private BooleanExpression goePrice(BigDecimal min){
        return min == null ? null : QProduct.product.price.goe(min);
    }
    private BooleanExpression loePrice(BigDecimal max){
        return max == null ? null : QProduct.product.price.loe(max);
    }
    private BooleanExpression stockPredicate(Boolean includeZero){
        if (includeZero == null) return null;
        return includeZero ? null : QProduct.product.stock.gt(0);
    }
}