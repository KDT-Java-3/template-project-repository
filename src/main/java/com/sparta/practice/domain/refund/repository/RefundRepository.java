package com.sparta.practice.domain.refund.repository;

import com.sparta.practice.domain.refund.entity.Refund;
import com.sparta.practice.domain.refund.entity.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    // 사용자별 환불 조회
    List<Refund> findByUserId(Long userId);

    // 사용자 + 환불 상태별 조회
    List<Refund> findByUserIdAndStatus(Long userId, RefundStatus status);

    // 주문별 환불 조회
    Optional<Refund> findByOrderId(Long orderId);
}
