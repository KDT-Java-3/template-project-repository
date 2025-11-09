package com.jaehyuk.week_01_project.domain.refund.repository;

import com.jaehyuk.week_01_project.domain.refund.entity.Refund;
import com.jaehyuk.week_01_project.domain.refund.enums.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByUserId(Long userId);
    List<Refund> findByUserIdAndStatus(Long userId, RefundStatus status);
}
