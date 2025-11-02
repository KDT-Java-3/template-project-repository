package com.sparta.demo1.domain.refund.repository;

import com.sparta.demo1.domain.refund.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<RefundEntity, Long> {
}
