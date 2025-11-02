package com.sparta.project1.domain.refund.repository;

import com.sparta.project1.domain.refund.entity.Refund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    @Query("select r from Refund r where r.users.id = :userId")
    Page<Refund> findAllByUserId(Long userId, Pageable pageable);
}
