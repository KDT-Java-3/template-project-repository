package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.QCategory;
import com.example.demo.repository.projection.CategoryStatsDto;
import com.example.demo.repository.projection.QCategoryStatsDto;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.entity.QCategory.category;
import static com.example.demo.entity.QProduct.product;

/**
 * 카테고리와 관련된 QueryDSL 예제 모음.
 */
@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * [기초 예제] 부모가 없는 루트 카테고리만 조회.
     */
    public List<Category> findRootCategories() {
        return queryFactory
                .selectFrom(category)
                .where(category.parent.isNull())
                .orderBy(category.createdAt.asc())
                .fetch();
    }

    /**
     * [Fetch Join 예제] 특정 카테고리와 직계 하위 카테고리를 한 번에 로딩.
     */
    public Category findCategoryWithChildren(Long categoryId) {
        return queryFactory
                .selectFrom(category)
                .leftJoin(category.children).fetchJoin()
                .where(category.id.eq(categoryId))
                .fetchOne();
    }

    /**
     * [집계 예제] 카테고리별 상품 개수, 하위 카테고리 개수 집계.
     */
    public List<CategoryStatsDto> fetchCategoryStats() {
        QCategory child = new QCategory("child");

        return queryFactory
                .select(new QCategoryStatsDto(
                        category.id,
                        category.name,
                        product.id.countDistinct(),
                        JPAExpressions
                                .select(child.id.count())
                                .from(child)
                                .where(child.parent.eq(category))
                ))
                .from(category)
                .leftJoin(product).on(product.category.eq(category))
                .groupBy(category.id, category.name)
                .orderBy(category.name.asc())
                .fetch();
    }
}
