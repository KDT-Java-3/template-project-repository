package io.depark.commerceservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.depark.commerceservice.controller.dto.PurchaseSearchCondition;
import io.depark.commerceservice.entity.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static io.depark.commerceservice.entity.QPurchase.purchase;

@Repository
@RequiredArgsConstructor
public class PurchaseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Purchase> searchProducts(PurchaseSearchCondition condition, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getUserId() != null) {
            builder.and(purchase.user.id.eq(condition.getUserId()));
        }

        if (condition.getStatus() != null) {
            builder.and(purchase.status.eq(condition.getStatus()));
        }

        if (condition.getStartDate() != null) {
            builder.and(purchase.createdAt.goe(condition.getStartDate().atStartOfDay()));
        }

        if (condition.getEndDate() != null) {
            builder.and(purchase.createdAt.lt(condition.getEndDate().plusDays(1).atStartOfDay()));
        }

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (pageable.getSort().isSorted()) {
            orderSpecifiers.addAll(
                    pageable.getSort().stream()
                            .map(
                                    order -> {
                                        Expression<? extends Comparable<?>> target = switch (order.getProperty()) {
                                            case "totalPrice" -> purchase.totalPrice;
                                            case "createdAt" -> purchase.createdAt;
                                            case "updatedAt" -> purchase.updatedAt;
                                            default -> purchase.id;
                                        };
                                        return order.isAscending() ? new OrderSpecifier<>(Order.ASC, target)
                                                : new OrderSpecifier<>(Order.DESC, target);
                                    }
                            ).toList()
            );
        }

        List<Purchase> content = queryFactory
                .selectFrom(purchase)
                .where(builder)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(purchase.count())
                .from(purchase)
                .where(builder);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
