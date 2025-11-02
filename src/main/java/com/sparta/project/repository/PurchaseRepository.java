package com.sparta.project.repository;

import com.sparta.project.common.enums.PurchaseStatus;
import com.sparta.project.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    // 특정 사용자의 주문 목록 조회
    @Query("SELECT o FROM purchase o LEFT JOIN FETCH o.user WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Purchase> findByUserId(@Param("userId") Long userId);

    // 주문 상세 조회 (연관 데이터 모두 포함)
    @Query("SELECT o FROM purchase o " +
            "LEFT JOIN FETCH o.user " +
            "LEFT JOIN FETCH o.orderItems oi " +
            "LEFT JOIN FETCH oi.product " +
            "WHERE o.id = :id")
    Optional<Purchase> findByIdWithDetails(@Param("id") Long id);

    // 특정 사용자의 특정 상태 주문 조회
    @Query("SELECT o FROM purchase purchase WHERE o.user.id = :userId AND o.status = :status")
    List<Purchase> findByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") PurchaseStatus status
    );
}