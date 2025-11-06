package com.example.demo.repository.practice;

import com.example.demo.repository.projection.CategoryOrderCountDto;
import com.example.demo.repository.projection.QCategoryOrderCountDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.entity.QCategory.category;
import static com.example.demo.entity.QProduct.product;
import static com.example.demo.entity.QPurchase.purchase;

/**
 * 실습 예제 2: groupBy 와 집계 함수를 사용하는 Repository.
 */
@Repository
@RequiredArgsConstructor
public class CategoryOrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 실습: 카테고리별 주문 건수를 집계한다.
     *
     * @return CategoryOrderCountDto 목록
     */
    public List<CategoryOrderCountDto> findCategoryOrderCounts() {
        return queryFactory
                .select(new QCategoryOrderCountDto(
                        category.name,
                        purchase.id.countDistinct()
                ))
                .from(purchase)
                .join(purchase.product, product)
                .join(product.category, category)
                .groupBy(category.name)
                .orderBy(purchase.id.countDistinct().desc())
                .fetch();
    }
}
