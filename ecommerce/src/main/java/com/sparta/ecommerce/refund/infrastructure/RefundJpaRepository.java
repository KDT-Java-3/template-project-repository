package com.sparta.ecommerce.refund.infrastructure;

import com.sparta.ecommerce.refund.domain.Refund;
import com.sparta.ecommerce.refund.domain.RefundStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RefundJpaRepository extends JpaRepository<Refund, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Refund r WHERE r.id = :id")
    Optional<Refund> findByIdForUpdate(@Param("id") Long id);

    List<Refund> findAllByUserIdAndStatus(Long userId, RefundStatus status);
}
