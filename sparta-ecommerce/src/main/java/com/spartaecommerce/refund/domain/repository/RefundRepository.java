package com.spartaecommerce.refund.domain.repository;

import com.spartaecommerce.refund.domain.entity.Refund;
import com.spartaecommerce.refund.domain.query.RefundSearchQuery;

import java.util.List;
import java.util.Optional;

public interface RefundRepository {

    Long save(Refund refund);

    Refund getById(Long refundId);

    Optional<Refund> findByOrderId(Long orderId);

    List<Refund> search(RefundSearchQuery searchQuery);
}