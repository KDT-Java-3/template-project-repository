package com.sparta.demo.refund.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    default Refund getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("환불 내역을 찾을 수 없습니다."));
    }

    List<Refund> findByUserId(Long userId);
}
