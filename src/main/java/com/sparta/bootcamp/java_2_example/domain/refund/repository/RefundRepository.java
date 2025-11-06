package com.sparta.bootcamp.java_2_example.domain.refund.repository;

import com.sparta.bootcamp.java_2_example.common.enums.RefundStatus;
import com.sparta.bootcamp.java_2_example.domain.refund.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByUserId(Long userId);

    List<Refund> findByUserIdAndStatus(Long userId, RefundStatus status);

    Optional<Refund> findByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);
}
