package com.example.week01_project.repository;

import com.example.week01_project.domain.refund.RefundItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundItemRepository extends JpaRepository<RefundItem, Long> {
    List<RefundItem> findByRefundId(Long refundId);
}
