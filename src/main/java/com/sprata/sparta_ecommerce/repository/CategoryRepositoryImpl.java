package com.sprata.sparta_ecommerce.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.QOrder;
import com.sprata.sparta_ecommerce.entity.QProduct;
import com.sprata.sparta_ecommerce.repository.projection.CategorySalesProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sprata.sparta_ecommerce.entity.QCategory.*;
import static com.sprata.sparta_ecommerce.entity.QOrder.*;
import static com.sprata.sparta_ecommerce.entity.QProduct.*;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl  implements CategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Category> findListPaging(PageDto pageDto) {
        return queryFactory.selectFrom(category)
                .offset(pageDto.getOffset())
                .limit(pageDto.getSize())
                .fetch();
    }

    @Override
    public List<CategorySalesProjection> findTop10SalesCategory() {
        // 판매량 합계 표현식 정의
        NumberExpression<Integer> totalSales = order.quantity.sum();

        return queryFactory
                .select(Projections.constructor(
                        CategorySalesProjection.class,
                        category.id,
                        category.name,
                        totalSales
                ))
                .from(category)
                .join(category.products, product)
                .join(product.orderList, order)
                .groupBy(category.id, category.name)
                .orderBy(totalSales.desc())
                .limit(10)
                .fetch();
    }
}
