package com.sprata.sparta_ecommerce.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.entity.QCategory;
import com.sprata.sparta_ecommerce.entity.QProduct;
import com.sprata.sparta_ecommerce.repository.projection.ProductCategoryProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.sum;
import static com.querydsl.core.types.ExpressionUtils.count;
import static com.sprata.sparta_ecommerce.entity.QCategory.*;
import static com.sprata.sparta_ecommerce.entity.QProduct.*;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> searchProducts(SearchProductDto searchDto, PageDto pageDto) {
        return queryFactory.selectFrom(product)
                .where(
                        isCategory(searchDto.getCategoryId()),
                        hasPriceRange(searchDto.getMinPrice(), searchDto.getMaxPrice()),
                        searchKeyword(searchDto.getKeyword())
                )
                .offset(pageDto.getOffset())
                .limit(pageDto.getSize())
                .fetch();
    }

    @Override
    public Product findByProductId(Long id) {
        return queryFactory.selectFrom(product)
                .where(product.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Product> findWithCategory(Long id) {
        return queryFactory.selectFrom(product)
                .join(product.category, category)
                .where(category.name.contains("전자제품"))
                .fetch()
                ;
    }

    @Override
    public Page<Product> findProductByPaging(Pageable pageable) {
        List<Product> list = queryFactory.selectFrom(product)
                .join(product.category, category).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long totalCount = queryFactory.select(product.count())
                .from(product)
                .join(product.category, category)
                .fetchOne();

        return new PageImpl<>(list, pageable, totalCount);
    }

    @Override
    public List<ProductCategoryProjection> findProductWithCategory() {
//        return queryFactory.select(Projections.fields(
//                                   ProductCategoryProjection.class,
//                                   product.id,
//                                   product.name,
//                                   product.price,
//                                   product.category.name.as("categoryName")
//                        ))
//                .from(product)
//                .join(product.category, category)
//                .where(category.name.contains("전자제품"))
//                .fetch()
//                ;

        return queryFactory.select(Projections.constructor(
                        ProductCategoryProjection.class,
                        product.category.name,
                        count(product.category),
                        sum(product.price)
                ))
                .from(product)
                .join(product.category, category)
                .where(category.name.contains("전자제품"))
                .groupBy(product.category.id)
                .fetch()
                ;
    }

    @Override
    public Optional<Product> findByProductName(String name) {
        return Optional.ofNullable(queryFactory.selectFrom(product)
                .where(product.name.eq(name))
                .fetchOne());
    }

    /**
     * 카테고리 조회 조건
     */
    private BooleanExpression isCategory(Long categoryId) {
        if (categoryId == null || categoryId == 0L) {
            return null;
        } else {
            return product.category.id.eq(categoryId);
        }
    }

    /**
     * 검색어 설정
     */
    private BooleanExpression searchKeyword(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return product.name.contains(keyword);
        } else {
            return null;
        }
    }

    /**
     * 금액 설정
     */
    private BooleanBuilder hasPriceRange(Long minPrice, Long maxPrice) {
        BooleanBuilder builder = new BooleanBuilder();
        if (minPrice != null && maxPrice != null) {
            builder.and(product.price.between(minPrice, maxPrice));
        } else if (minPrice != null) {
            builder.and(product.price.goe(minPrice));
        } else if (maxPrice != null) {
            builder.and(product.price.loe(maxPrice));
        }
        return builder;
    }


}
