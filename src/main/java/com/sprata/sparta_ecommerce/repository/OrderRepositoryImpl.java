package com.sprata.sparta_ecommerce.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.entity.QOrder;
import com.sprata.sparta_ecommerce.entity.QProduct;
import com.sprata.sparta_ecommerce.service.dto.OrderServiceSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.sprata.sparta_ecommerce.entity.QOrder.*;
import static com.sprata.sparta_ecommerce.entity.QProduct.*;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findByProductWhenComplete(Long productId) {
        return queryFactory.selectFrom(order)
                .join(order.product, product).fetchJoin()
                .where(
                        order.product.id.eq(productId)
                        ,order.status.eq(OrderStatus.COMPLETED)
                )
                .fetch();
    }

    @Override
    public List<Order> findByUserWithPaging(OrderServiceSearchDto searchDto, PageDto pageDto) {
        return queryFactory.selectFrom(order)
                .join(order.product, product).fetchJoin()
                .where(order.userId.eq(searchDto.getUserId())
                        ,searchOrderStatus(searchDto.getOrderStatus())
                        ,searchOrderDate(searchDto.getStartDate(), searchDto.getEndDate())
                )
                .offset(pageDto.getOffset())
                .limit(pageDto.getSize())
                .fetch();
    }

    private BooleanExpression searchOrderDate(LocalDate startDate, LocalDate endDate) {
        if(startDate != null && endDate != null) {
            return order.orderDate.between(startDate, endDate);
        }
        return null;
    }

    private BooleanExpression searchOrderStatus(String status) {
        if(StringUtils.hasText(status)){
            return order.status.eq(OrderStatus.valueOf(status));
        }else {
            return null;
        }
    }
}
