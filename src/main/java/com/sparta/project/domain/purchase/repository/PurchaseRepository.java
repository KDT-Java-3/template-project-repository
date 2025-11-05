package com.sparta.project.domain.purchase.repository;


import com.sparta.project.common.enums.PurchaseStatus;
import com.sparta.project.domain.purchase.entity.Purchase;
import com.sparta.project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // 특정 사용자의 주문을 생성일 기준 내림차순으로 조회
    List<Purchase> findByUserOrderByCreatedAtDesc(User user);
}