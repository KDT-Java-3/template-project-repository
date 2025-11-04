package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 사용자 조회
     * @param email 이메일
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * 이메일 존재 여부 확인
     * @param email 이메일
     * @return 존재 여부
     */
    boolean existsByEmail(String email);

    /**
     * 모든 사용자 조회 (N+1 문제 발생)
     * @return 사용자 목록
     */
    List<User> findAll();

    /**
     * Fetch Join을 사용하여 사용자와 주문을 한 번에 조회 (N+1 문제 해결)
     * @return 사용자 목록 (주문 정보 포함)
     */
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.purchases")
    List<User> findAllWithPurchases();

    /**
     * Fetch Join을 사용하여 사용자, 주문, 주문 상품, 상품을 한 번에 조회
     * @return 사용자 목록 (모든 연관 데이터 포함)
     */
    @Query("SELECT DISTINCT u FROM User u " +
           "LEFT JOIN FETCH u.purchases p " +
           "LEFT JOIN FETCH p.purchaseItems pi " +
           "LEFT JOIN FETCH pi.product")
    List<User> findAllWithPurchasesAndItems();
}

