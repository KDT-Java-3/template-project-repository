package io.depark.commerceservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.depark.commerceservice.controller.dto.ProductSearchCondition;
import io.depark.commerceservice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static io.depark.commerceservice.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Product> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 카테고리
        if (condition.getCategoryId() != null) {
            builder.and(product.category.id.eq(condition.getCategoryId()));
        }

        // 이름
        if (condition.getName() != null) {
            builder.and(product.name.contains(condition.getName()));
        }

        // 가격 범위
        if (condition.getMinPrice() != null) {
            builder.and(product.price.goe(condition.getMinPrice()));
        }
        if (condition.getMaxPrice() != null) {
            builder.and(product.price.loe(condition.getMaxPrice()));
        }

        // 재고
        if (condition.getIncludeZeroStock() != null && condition.getIncludeZeroStock()) {
            builder.and(product.stock.goe(0L));
        } else {
            builder.and(product.stock.gt(0L));
        }

        List<Product> content = queryFactory
                .selectFrom(product)
                .leftJoin(product.category)
                .fetchJoin()
                .where(builder)
                .orderBy(getOrderSpecifiers(condition, pageable).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(product.count())
                .from(product)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, totalCount != null ? totalCount : 0L);
    }

    // 동적 정렬 조건 생성
    private List<OrderSpecifier<?>> getOrderSpecifiers(ProductSearchCondition condition, Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (condition.getSortBy() != null && condition.getSortDirection() != null) {
            Order direction = condition.getSortDirection().equalsIgnoreCase("DESC") ? Order.DESC : Order.ASC;
            PathBuilder<Product> entityPath = new PathBuilder<>(Product.class, "product");
            orderSpecifiers.add(new OrderSpecifier<>(direction, entityPath.get(condition.getSortBy(), Comparable.class)));
        }

        if (pageable.getSort().isSorted()) {
            orderSpecifiers.addAll(
                    pageable.getSort().stream()
                            .map(
                                    order -> {
                                        Expression<? extends Comparable<?>> target = switch (order.getProperty()) {
                                            case "name" -> product.price;
                                            case "price" -> product.stock;
                                            case "createdAt" -> product.createdAt;
                                            case "updatedAt" -> product.updatedAt;
                                            default -> product.id;
                                        };
                                        return order.isAscending() ? new OrderSpecifier<>(Order.ASC, target)
                                                : new OrderSpecifier<>(Order.DESC, target);
                                    }
                            ).toList()
            );
        }

        if (orderSpecifiers.isEmpty()) {
            orderSpecifiers.add(product.id.desc());
        }

        return orderSpecifiers;
    }
}
