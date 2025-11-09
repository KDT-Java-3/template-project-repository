package com.sparta.demo1.domain.refund.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.demo1.domain.product.entity.QProductEntity;
import com.sparta.demo1.domain.purchase.entity.QPurchaseEntity;
import com.sparta.demo1.domain.purchase.entity.QPurchaseProductEntity;
import com.sparta.demo1.domain.refund.entity.QRefundEntity;
import com.sparta.demo1.domain.refund.entity.RefundEntity;
import com.sparta.demo1.domain.refund.enums.RefundStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RefundQueryDsl {
    private final JPAQueryFactory jpaQueryFactory;

    QRefundEntity qRefundEntity = QRefundEntity.refundEntity;
    QPurchaseEntity qPurchaseEntity = QPurchaseEntity.purchaseEntity;
    QPurchaseProductEntity qPurchaseProductEntity = QPurchaseProductEntity.purchaseProductEntity;
    QProductEntity qProductEntity = QProductEntity.productEntity;

    public Optional<RefundEntity> getRefundOfPurchaseAndProductById(Long id){
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(qRefundEntity)
                        .join(qRefundEntity.purchase, qPurchaseEntity).fetchJoin()
                        .join(qPurchaseEntity.purchaseProductList, qPurchaseProductEntity).fetchJoin()
                        .join(qPurchaseProductEntity.product, qProductEntity).fetchJoin()
                        .where(qRefundEntity.id.eq(id))
                .fetchOne());
    }

    public Page<RefundEntity> getRefundAdminFilter(RefundStatus refundStatus, Pageable pageable){
        List<RefundEntity> refundEntityList = jpaQueryFactory
                .selectFrom(qRefundEntity)
                .where(qRefundEntity.status.eq(refundStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qRefundEntity)
                .where(qRefundEntity.status.eq(refundStatus))
                .stream().count();

        return new PageImpl<>(refundEntityList, pageable, total);
    }

    public Page<RefundEntity> getRefundFilter(Long userId, RefundStatus refundStatus, Pageable pageable){
        List<RefundEntity> refundEntityList = jpaQueryFactory
                .selectFrom(qRefundEntity)
                .where(
                        qRefundEntity.user.id.eq(userId)
                                .and(qRefundEntity.status.eq(refundStatus))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qRefundEntity)
                .where(
                        qRefundEntity.user.id.eq(userId)
                                .and(qRefundEntity.status.eq(refundStatus))
                )
                .stream().count();

        return new PageImpl<>(refundEntityList, pageable, total);
    }
}
