package com.jaehyuk.week_01_project.domain.purchase.repository;

import com.jaehyuk.week_01_project.domain.purchase.entity.Purchase;
import com.jaehyuk.week_01_project.domain.purchase.enums.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    /**
     * 특정 사용자의 모든 주문 조회
     */
    List<Purchase> findByUserId(Long userId);

    /**
     * 특정 사용자의 특정 상태 주문 조회
     */
    List<Purchase> findByUserIdAndStatus(Long userId, PurchaseStatus status);
}
