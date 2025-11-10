package com.example.demo.common.repository.support;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

public abstract class Querydsl7RepositorySupport<T> {
    protected final EntityManager em;
    protected final JPAQueryFactory queryFactory;
    protected final Querydsl querydsl;
    protected final EntityPath<T> defaultPath;

    protected Querydsl7RepositorySupport(Class<T> domainClass, EntityManager em, JPAQueryFactory qf) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(em, "EntityManager must not be null!");
        Assert.notNull(qf, "JPAQueryFactory must not be null!");

        this.em = em;
        this.queryFactory = qf;

        var entityInfo = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
        var resolver = SimpleEntityPathResolver.INSTANCE;
        @SuppressWarnings("unchecked")
        EntityPath<T> path = (EntityPath<T>) resolver.createPath(entityInfo.getJavaType());
        this.defaultPath = path;

        this.querydsl = new Querydsl(em, new PathBuilder<>(path.getType(), path.getMetadata()));
    }

    /** 프로젝션 선택 */
    protected <R> JPAQuery<R> select(Expression<R> expr) { return queryFactory.select(expr); }

    /** from 선택 */
    protected JPAQuery<T> selectFrom(EntityPath<T> from) { return queryFactory.selectFrom(from); }

    // ========= 오프셋 기반 페이지네이션 =========

    // 복잡 조인/그룹핑이면 아래 시그니처 사용 권장
    /** 오프셋 페이징 (본문/카운트 빌더를 모두 명시) — 가장 안전 */
    protected <R> Page<R> applyOffsetPagination(
            Pageable pageable,
            Function<JPAQueryFactory, JPAQuery<R>> contentBuilder,
            Function<JPAQueryFactory, JPAQuery<Long>> countBuilder
    ) {
        var contentQuery = contentBuilder.apply(queryFactory);
        List<R> content = querydsl.applyPagination(pageable, contentQuery).fetch();

        return PageableExecutionUtils.getPage(
                content, pageable,
                () -> {
                    Long cnt = countBuilder.apply(queryFactory).fetchOne();
                    return cnt != null ? cnt : 0L;
                }
        );
    }

    /** 오프셋 페이징 (본문만 전달하면 내부에서 count 유도) — 간단 케이스용 */
    protected Page<T> applyOffsetPagination(
            Pageable pageable,
            Function<JPAQueryFactory, JPAQuery<T>> contentBuilder
    ) {
        var contentQuery = contentBuilder.apply(queryFactory);
        List<T> content = querydsl.applyPagination(pageable, contentQuery).fetch();

        // 본문 쿼리를 복제해서 count(*) 생성 (정렬 제거)
        var countQuery = contentQuery.clone(em)
                .select(Wildcard.count)
                .orderBy();

        return PageableExecutionUtils.getPage(
                content, pageable,
                () -> {
                    Long cnt = countQuery.fetchOne();
                    return cnt != null ? cnt : 0L;
                }
        );
    }

    // ========= 커서 기반 페이지네이션 =========

    /**
     * 커서 페이징 (ID 컬럼 기준)
     * @param contentBuilder 본문 쿼리 빌더
     * @param idPath 정렬/커서 기준 ID Path (예: QFood.food.id)
     * @param pageSize 페이지 크기
     * @param lastId 이전 페이지 마지막 ID (없으면 null)
     * @param asc 정렬 방향
     */
    protected <R> Slice<R> applyCursorPagination(
            Function<JPAQueryFactory, JPAQuery<R>> contentBuilder,
            NumberPath<Long> idPath,
            int pageSize,
            Long lastId,
            boolean asc
    ) {
        var base = contentBuilder.apply(queryFactory);
        var filtered = (lastId == null)
                ? base
                : (asc ? base.where(idPath.gt(lastId)) : base.where(idPath.lt(lastId)));

        List<R> content = filtered
                .orderBy(asc ? idPath.asc() : idPath.desc())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = content.size() > pageSize;
        if (hasNext) content.remove(pageSize);

        return new SliceImpl<>(content, PageRequest.ofSize(pageSize), hasNext);
    }
}
