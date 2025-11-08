package com.sprata.sparta_ecommerce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.entity.QOrder;
import com.sprata.sparta_ecommerce.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
