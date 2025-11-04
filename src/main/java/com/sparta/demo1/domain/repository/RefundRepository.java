package com.sparta.demo1.domain.repository;

import com.sparta.demo1.domain.entity.Order;
import com.sparta.demo1.domain.entity.Refund;
import com.sparta.demo1.domain.entity.RefundStatus;
import com.sparta.demo1.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

  // 특정 사용자의 환불 요청 목록 조회 (최신순)
  List<Refund> findByUserOrderByCreatedAtDesc(User user);

  // 특정 사용자의 환불 요청 목록 조회
  List<Refund> findByUser(User user);

  // 환불 상태별 조회
  List<Refund> findByStatus(RefundStatus status);

  // 특정 사용자의 특정 상태 환불 조회
  List<Refund> findByUserAndStatus(User user, RefundStatus status);

  // 특정 사용자의 특정 상태 환불 조회 (최신순)
  List<Refund> findByUserAndStatusOrderByCreatedAtDesc(User user, RefundStatus status);

  // 특정 주문의 환불 조회 (중복 체크용)
  Optional<Refund> findByOrder(Order order);

  // 전체 환불 목록 조회 (최신순) - 관리자용
  List<Refund> findAllByOrderByCreatedAtDesc();
}
