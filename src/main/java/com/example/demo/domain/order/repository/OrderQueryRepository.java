package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.dto.request.OrderSearchCondition;
import com.example.demo.domain.order.dto.response.OrderSummary;
import com.example.demo.domain.order.entity.QOrder;
import com.example.demo.domain.order.entity.QOrderItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Order QueryDSL Repository
 */
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 주문 목록 동적 검색 (페이징)
     */
    public Page<OrderSummary> search(OrderSearchCondition condition, Pageable pageable) {
        QOrder order = QOrder.order;
        QOrderItem orderItem = QOrderItem.orderItem;

        BooleanBuilder builder = new BooleanBuilder();

        // userId 필터
        if (condition.getUserId() != null) {
            builder.and(order.userId.eq(condition.getUserId()));
        }

        // status 필터
        if (condition.getStatus() != null) {
            builder.and(order.status.eq(condition.getStatus()));
        }

        // 날짜 범위 필터
        if (condition.getStartDate() != null) {
            builder.and(order.orderDate.goe(condition.getStartDate()));
        }
        if (condition.getEndDate() != null) {
            builder.and(order.orderDate.loe(condition.getEndDate()));
        }

        // 총 개수 조회
        Long total = queryFactory
            .select(Wildcard.count)
            .from(order)
            .where(builder)
            .fetchOne();

        // 데이터 조회
        List<OrderSummary> content = queryFactory
            .select(Projections.constructor(OrderSummary.class,
                order.id,
                order.userId,
                order.status,
                order.orderDate,
                orderItem.count().intValue().as("itemCount"),
                orderItem.unitPrice.multiply(orderItem.quantity).sum().as("totalAmount"),
                order.createdAt
            ))
            .from(order)
            .leftJoin(orderItem).on(orderItem.order.id.eq(order.id))
            .where(builder)
            .groupBy(order.id)
            .orderBy(buildOrderSpecifiers(pageable).toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    /**
     * 정렬 조건 생성
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<OrderSpecifier<?>> buildOrderSpecifiers(Pageable pageable) {
        QOrder order = QOrder.order;
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (pageable.getSort().isEmpty()) {
            orderSpecifiers.add(new OrderSpecifier(com.querydsl.core.types.Order.DESC, order.createdAt));
        } else {
            pageable.getSort().forEach(sortOrder -> {
                com.querydsl.core.types.Order direction = sortOrder.isAscending()
                    ? com.querydsl.core.types.Order.ASC
                    : com.querydsl.core.types.Order.DESC;

                switch (sortOrder.getProperty()) {
                    case "orderDate" -> orderSpecifiers.add(new OrderSpecifier(direction, order.orderDate));
                    case "status" -> orderSpecifiers.add(new OrderSpecifier(direction, order.status));
                    case "createdAt" -> orderSpecifiers.add(new OrderSpecifier(direction, order.createdAt));
                    default -> orderSpecifiers.add(new OrderSpecifier(direction, order.id));
                }
            });
        }

        return orderSpecifiers;
    }
}
