package com.example.week01_project.repository;

import com.example.week01_project.domain.orders.Orders;
import com.example.week01_project.domain.orders.QOrders;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory query;
    public OrderQueryRepositoryImpl(JPAQueryFactory query) { this.query = query; }

    @Override
    public Page<Orders> search(Long userId, Orders.Status status, LocalDate from, LocalDate to, Pageable pageable) {
        QOrders o = QOrders.orders;

        var base = query.selectFrom(o)
                .where(
                        eqUser(userId),
                        eqStatus(status),
                        betweenDate(o, from, to)
                );

        long total = base.fetch().size();
        List<Orders> content = base
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QuerydslSortUtil.toOrders(pageable.getSort(), o))
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression eqUser(Long userId){ return userId==null? null : QOrders.orders.userId.eq(userId); }
    private BooleanExpression eqStatus(Orders.Status status){ return status==null? null : QOrders.orders.status.eq(status); }
    private BooleanExpression betweenDate(QOrders o, LocalDate from, LocalDate to){
        if (from==null && to==null) return null;
        LocalDateTime s = from==null? LocalDate.MIN.atStartOfDay() : from.atStartOfDay();
        LocalDateTime e = to==null? LocalDate.MAX.atTime(23,59,59) : to.atTime(23,59,59);
        return o.createdAt.between(s, e);
    }
}