package com.example.stproject.domain.product.repository;

import com.example.stproject.domain.product.dto.ProductSearchCond;
import com.example.stproject.domain.product.entity.Product;
import com.example.stproject.domain.product.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Product> search(ProductSearchCond cond, Pageable pageable) {
        QProduct p = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if (cond.getCategoryId() != null) builder.and(p.category.id.eq(cond.getCategoryId()));
        if (cond.getMinPrice() != null)    builder.and(p.price.goe(cond.getMinPrice()));
        if (cond.getMaxPrice() != null)    builder.and(p.price.loe(cond.getMaxPrice()));
        if (hasText(cond.getKeyword()))    builder.and(p.name.containsIgnoreCase(cond.getKeyword()));

        List<Product> content = queryFactory.selectFrom(p)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(p.count()).from(p).where(builder).fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
