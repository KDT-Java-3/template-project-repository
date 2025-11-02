package com.sparta.sangmin.repository;

import com.sparta.sangmin.domain.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByUserId(Long userId);
}
