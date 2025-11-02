package com.sparta.demo.domain.order.repository;

import com.sparta.demo.domain.order.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findAllByUserId(Long userId);
}

