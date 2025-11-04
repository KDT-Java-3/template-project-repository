package com.example.demo.repository;

import com.example.demo.entity.Refund;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    /**
     * 사용자별 환불 조회
     * @param user 사용자
     * @return 환불 목록
     */
    List<Refund> findByUser(User user);

    /**
     * 사용자 ID로 환불 조회
     * @param userId 사용자 ID
     * @return 환불 목록
     */
    List<Refund> findByUserId(Long userId);
}

