package com.sparta.project.domain.refund.repository;

import com.sparta.project.domain.purchase.entity.Purchase;
import com.sparta.project.domain.refund.entity.Refund;
import com.sparta.project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    // 특정 사용자의 환불을 생성일 기준 내림차순으로 조회
    List<Refund> findByUserOrderByCreatedAtDesc(User user);

    // 특정 주문의 환불 존재 여부
    boolean existsByPurchase(Purchase purchase);

}