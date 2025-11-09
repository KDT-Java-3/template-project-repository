package com.sparta.demo1.domain.purchase.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.demo1.domain.product.entity.ProductEntity;
import com.sparta.demo1.domain.product.entity.QProductEntity;
import com.sparta.demo1.domain.product.enums.ProductOrderBy;
import com.sparta.demo1.domain.product.enums.ProductStockFilter;
import com.sparta.demo1.domain.purchase.entity.PurchaseEntity;
import com.sparta.demo1.domain.purchase.entity.PurchaseProductEntity;
import com.sparta.demo1.domain.purchase.entity.QPurchaseEntity;
import com.sparta.demo1.domain.purchase.entity.QPurchaseProductEntity;
import com.sparta.demo1.domain.purchase.enums.PurchaseOrderBy;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PurchaseQueryDsl {
    private final JPAQueryFactory jpaQueryFactory;

    private final QPurchaseEntity qPurchaseEntity = QPurchaseEntity.purchaseEntity;
    private final QPurchaseProductEntity qPurchaseProductEntity = QPurchaseProductEntity.purchaseProductEntity;

    private final QProductEntity qProductEntity = QProductEntity.productEntity;

    public List<PurchaseProductEntity> findPurchasesOfCompletedStateByProductId(Long productId) {
        return jpaQueryFactory
                .selectFrom(qPurchaseProductEntity)
                .where(
                        qPurchaseProductEntity.product.id.eq(productId)
                                .and(qPurchaseProductEntity.purchase.status.eq(PurchaseStatus.COMPLETED))
                )
                .fetch();
    }

    public Optional<PurchaseEntity> findAllProductInfoById(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(qPurchaseEntity)
                .join(qPurchaseEntity.purchaseProductList, qPurchaseProductEntity).fetchJoin()
                .join(qPurchaseProductEntity.product, qProductEntity).fetchJoin()
                .where(qPurchaseEntity.id.eq(id))
                .fetchOne());
    }

    public Page<PurchaseEntity> findPurchaseOfFilter(
            Long userId,
            PurchaseStatus purchaseStatus,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable,
            List<PurchaseOrderBy> orderByList
    ){

        List<OrderSpecifier<?>> orderSpecifier = getOrderSpecifiers(orderByList);

        List<PurchaseEntity> purchaseEntityList = jpaQueryFactory
                .selectFrom(qPurchaseEntity)
                .where(qPurchaseEntity.user.id.eq(userId)
                        .and(dynamicPurchaseBuilder(purchaseStatus, startDate, endDate))
                )
                .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qPurchaseEntity)
                .where(
                        qPurchaseEntity.user.id.eq(userId)
                                .and(dynamicPurchaseBuilder(purchaseStatus, startDate, endDate))
                )
                .stream().count();
        return new PageImpl<>(purchaseEntityList, pageable, total);
    }

    private BooleanBuilder dynamicPurchaseBuilder(
            PurchaseStatus purchaseStatus,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(startDate != null){
            booleanBuilder.and(qProductEntity.createdAt.goe(startDate));
        }

        if(endDate != null){
            booleanBuilder.and(qProductEntity.createdAt.loe(endDate));
        }

        if (purchaseStatus != null) {
            booleanBuilder.and(qPurchaseEntity.status.eq(purchaseStatus));
        }

        return booleanBuilder;
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(List<PurchaseOrderBy> orderByList) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (orderByList == null || orderByList.isEmpty()) {
            orders.add(qPurchaseEntity.createdAt.desc()); // 기본값: 최신순
            return orders;
        }

        for (PurchaseOrderBy orderBy : orderByList) {
            switch (orderBy) {
                case CREATED_AT_ASC:
                    orders.add(qProductEntity.createdAt.asc());
                    break;
                case CREATED_AT_DESC:
                    orders.add(qProductEntity.createdAt.desc());
                    break;
            }
        }

        return orders;
    }
}
