package com.sparta.project.domain.purcharse.repository;

import com.sparta.project.domain.purcharse.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}