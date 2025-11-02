package com.sparta.demo1.domain.purchase.repository;

import com.sparta.demo1.domain.purchase.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}
