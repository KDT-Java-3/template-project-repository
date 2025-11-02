package com.spartaecommerce.refund.infrastructure.persistence.jpa.repository;

import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.refund.domain.entity.Refund;
import com.spartaecommerce.refund.domain.query.RefundSearchQuery;
import com.spartaecommerce.refund.domain.repository.RefundRepository;
import com.spartaecommerce.refund.infrastructure.persistence.jpa.entity.RefundJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {

    private final RefundJpaRepository refundJpaRepository;

    @Override
    public Long save(Refund refund) {
        RefundJpaEntity refundJpaEntity = RefundJpaEntity.from(refund);
        return refundJpaRepository.save(refundJpaEntity).getRefundId();
    }

    @Override
    public Refund getById(Long refundId) {
        RefundJpaEntity refundJpaEntity = refundJpaRepository.findById(refundId)
            .orElseThrow(() -> new BusinessException(
                ErrorCode.ENTITY_NOT_FOUND,
                "refundId: " + refundId
            ));

        return refundJpaEntity.toDomain();
    }

    @Override
    public Optional<Refund> findByOrderId(Long orderId) {
        return refundJpaRepository.findByOrderId(orderId)
            .map(RefundJpaEntity::toDomain);
    }

    @Override
    public List<Refund> search(RefundSearchQuery searchQuery) {
        List<RefundJpaEntity> refundJpaEntities = refundJpaRepository.findAllByUserId(searchQuery.userId());

        return refundJpaEntities.stream()
            .map(RefundJpaEntity::toDomain)
            .toList();
    }
}