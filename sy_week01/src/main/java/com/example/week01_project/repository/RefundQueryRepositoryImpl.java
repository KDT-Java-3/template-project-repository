package com.example.week01_project.repository;

import com.example.week01_project.domain.refund.QRefund;
import com.example.week01_project.domain.refund.Refund;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RefundQueryRepositoryImpl implements RefundQueryRepository {
    private final JPAQueryFactory query;
    public RefundQueryRepositoryImpl(JPAQueryFactory query) { this.query = query; }

    @Override
    public Page<Refund> search(Long userId, Refund.Status status, Pageable pageable) {
        var r = QRefund.refund;
        var base = query.selectFrom(r)
                .where(
                        userId==null? null : r.userId.eq(userId),
                        status==null? null : r.status.eq(status)
                );
        long total = base.fetch().size();
        List<Refund> content = base.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(r.createdAt.desc())
                .fetch();
        return new PageImpl<>(content, pageable, total);
    }
}
