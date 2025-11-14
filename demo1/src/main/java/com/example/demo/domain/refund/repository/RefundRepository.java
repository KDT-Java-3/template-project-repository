package com.example.demo.domain.refund.repository;


import com.example.demo.domain.refund.entity.Refund;
import com.example.demo.domain.refund.service.RefundService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund,Long> {
    List<Refund> findRefundsByUserId(Long id);
}
