package com.sparta.demo.repository;

import com.sparta.demo.domain.refund.Refund;
import com.sparta.demo.domain.refund.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    List<Refund> findByUser_Id(Long userId);

    List<Refund> findByUser_IdAndStatus(Long userId, RefundStatus status);
}
