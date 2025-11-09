package com.sparta.demo1.domain.purchase.repository;

import com.sparta.demo1.domain.purchase.entity.PurchaseProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProductEntity, Long> {
}
