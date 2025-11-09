package com.example.week01_project.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QuerydslSortUtil {
    public static OrderSpecifier<?>[] toOrders(Sort sort, Object root) {
        if (sort == null) return new OrderSpecifier[0];
        PathBuilder<?> entityPath = new PathBuilder<>(root.getClass(), root.getClass().getSimpleName().toLowerCase());
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        for (Sort.Order o : sort) {
            var direction = o.isAscending()? Order.ASC : Order.DESC;
            orders.add(new OrderSpecifier(direction, entityPath.get(o.getProperty())));
        }
        return orders.toArray(OrderSpecifier[]::new);
    }
}