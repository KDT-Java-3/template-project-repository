package com.sparta.ecommerce.domain.refund.repository;

import com.sparta.ecommerce.domain.refund.entity.Refund;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByUserId(Long id);
}
