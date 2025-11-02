package com.sparta.ecommerce.purchase.infrastructure;

import com.sparta.ecommerce.purchase.domain.Purchase;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseJpaRepository extends JpaRepository<Purchase, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select P from Purchase P where P.id = :id")
    Optional<Purchase> findByIdForUpdate(@Param("id") Long id);

    List<Purchase> findAllByUserId(Long userId);

}
