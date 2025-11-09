package com.sparta.demo1.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.demo1.domain.category.entity.CategoryEntity;
import com.sparta.demo1.domain.category.entity.QCategoryEntity;
import com.sparta.demo1.domain.product.entity.QProductEntity;
import com.sparta.demo1.domain.purchase.entity.QPurchaseProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryQueryDsl {
    private final JPAQueryFactory jpaQueryFactory;

    private final QCategoryEntity qCategoryEntity = QCategoryEntity.categoryEntity;
    private final QProductEntity qProductEntity = QProductEntity.productEntity;
    private final QPurchaseProductEntity qPurchaseProductEntity = QPurchaseProductEntity.purchaseProductEntity;

    public Optional<CategoryEntity> findCategoryExistChildren(Long id){
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(qCategoryEntity)
                .join(qCategoryEntity.childrenCategoryList, qCategoryEntity).fetchJoin()
                .join(qCategoryEntity.productList, qProductEntity).fetchJoin()
                .where(qCategoryEntity.id.eq(id))
                .fetchOne());
    }

    public List<CategoryEntity> findCategoryTop10Sale(){
        return jpaQueryFactory
                .selectFrom(qCategoryEntity)
                .join(qCategoryEntity.productList, qProductEntity)
                .join(qProductEntity.purchaseProductList, qPurchaseProductEntity)
                .groupBy(qCategoryEntity.id)
                .orderBy(qPurchaseProductEntity.quantity.sum().desc())
                .limit(10)
                .fetch();
    }
}
