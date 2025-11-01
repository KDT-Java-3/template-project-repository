package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByUserId(Long userId);
}
