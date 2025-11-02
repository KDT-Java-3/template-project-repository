package com.example.demo.domain.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Spring의 Repository Bean으로 등록 (생략 가능하지만 명시적으로 표현)
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}