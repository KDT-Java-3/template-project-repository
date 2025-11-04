package com.example.demo.repository;

import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    /**
     * 사용자별 주문 조회
     * @param user 사용자
     * @return 주문 목록
     */
    List<Purchase> findByUser(User user);

    /**
     * 사용자 ID로 주문 조회
     * @param userId 사용자 ID
     * @return 주문 목록
     */
    List<Purchase> findByUserId(Long userId);

    /**
     * Fetch Join을 사용하여 주문과 주문 상품을 한 번에 조회
     * @param userId 사용자 ID
     * @return 주문 목록 (주문 상품 정보 포함)
     */
    @Query("SELECT DISTINCT p FROM Purchase p " +
           "LEFT JOIN FETCH p.purchaseItems pi " +
           "LEFT JOIN FETCH pi.product " +
           "WHERE p.user.id = :userId")
    List<Purchase> findByUserIdWithItems(Long userId);
}

