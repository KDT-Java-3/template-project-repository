package com.example.week01_project.repository;

import com.example.week01_project.domain.refund.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByUserIdOrderByCreatedAtDesc(Long userId);
}
