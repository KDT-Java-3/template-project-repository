package io.depark.commerceservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.depark.commerceservice.controller.dto.RefundSearchCondition;
import io.depark.commerceservice.entity.Refund;
import io.depark.commerceservice.service.dto.RefundDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.group.GroupBy.list;
import static io.depark.commerceservice.entity.QProduct.product;
import static io.depark.commerceservice.entity.QPurchase.purchase;
import static io.depark.commerceservice.entity.QPurchaseProduct.purchaseProduct;
import static io.depark.commerceservice.entity.QRefund.refund;

@Repository
@RequiredArgsConstructor
public class RefundQueryRepository {

    private final JPAQueryFactory queryFactory;

    public RefundDetailResult findRefundDetails(Long id) {
        return queryFactory
                .from(refund)
                .join(refund.purchase, purchase)
                .join(purchase.purchaseProducts, purchaseProduct)
                .join(purchaseProduct.product, product)
                .where(refund.id.eq(id))
                .transform(GroupBy.groupBy(refund.id).as(
                        Projections.constructor(RefundDetailResult.class,
                                refund.id,
                                purchase.id,
                                refund.status,
                                refund.reason,
                                list(
                                        Projections.constructor(RefundDetailResult.PurchaseProductInfo.class,
                                                product.id,
                                                product.name,
                                                purchaseProduct.quantity,
                                                purchaseProduct.price,
                                                purchaseProduct.price.multiply(purchaseProduct.quantity)
                                        )
                                ),
                                refund.createdAt,
                                refund.updatedAt
                        )
                ))
                .get(id);
    }

    public Page<Refund> searchRefunds(RefundSearchCondition condition, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getStatus() != null) {
            builder.and(refund.status.eq(condition.getStatus()));
        }

        if (condition.getStartDate() != null) {
            builder.and(refund.createdAt.goe(condition.getStartDate().atStartOfDay()));
        }

        if (condition.getEndDate() != null) {
            builder.and(refund.createdAt.lt(condition.getEndDate().plusDays(1).atStartOfDay()));
        }

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (pageable.getSort().isSorted()) {
            orderSpecifiers.addAll(
                    pageable.getSort().stream()
                            .map(
                                    order -> {
                                        Expression<? extends Comparable<?>> target = switch (order.getProperty()) {
                                            case "status" -> refund.status;
                                            case "createdAt" -> refund.createdAt;
                                            case "updatedAt" -> refund.updatedAt;
                                            default -> refund.id;
                                        };
                                        return order.isAscending() ? new OrderSpecifier<>(Order.ASC, target)
                                                : new OrderSpecifier<>(Order.DESC, target);
                                    }
                            ).toList()
            );
        }

        List<Refund> content = queryFactory
                .selectFrom(refund)
                .where(builder)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(refund.count())
                .from(refund)
                .where(builder);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
