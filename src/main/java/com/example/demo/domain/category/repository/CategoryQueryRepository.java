package com.example.demo.domain.category.repository;

import com.example.demo.domain.category.dto.response.CategoryTopProductDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Category QueryDSL Repository
 */
@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 카테고리별 최다 판매 상품 Top 10 조회
     *
     * TODO: OrderItem Entity 구현 후 실제 쿼리 작성
     * - OrderItem과 Product를 LEFT JOIN
     * - categoryId로 필터링
     * - quantity를 SUM하여 totalSales 계산
     * - totalSales DESC로 정렬
     * - LIMIT 10
     *
     * 예상 쿼리:
     * SELECT p.id, p.name, p.price, SUM(oi.quantity) as totalSales
     * FROM order_items oi
     * LEFT JOIN products p ON oi.product_id = p.id
     * WHERE p.category_id = :categoryId
     * GROUP BY p.id
     * ORDER BY totalSales DESC
     * LIMIT 10
     */
    public List<CategoryTopProductDto> findTop10BySales(Long categoryId) {
        // TODO: OrderItem Entity 구현 후 실제 쿼리 작성
        // QOrderItem orderItem = QOrderItem.orderItem;
        // QProduct product = QProduct.product;
        //
        // return queryFactory
        //     .select(Projections.constructor(CategoryTopProductDto.class,
        //         product.id,
        //         product.name,
        //         product.price,
        //         orderItem.quantity.sum().as("totalSales")))
        //     .from(orderItem)
        //     .leftJoin(product).on(orderItem.productId.eq(product.id))
        //     .where(product.categoryId.eq(categoryId))
        //     .groupBy(product.id)
        //     .orderBy(orderItem.quantity.sum().desc())
        //     .limit(10)
        //     .fetch();

        throw new UnsupportedOperationException("OrderItem Entity 구현 필요");
    }
}
