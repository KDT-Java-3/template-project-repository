package com.example.demo.repository;

import com.example.demo.PurchaseStatus;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    boolean existsByProductIdAndStatus(Long productId, PurchaseStatus status);
}

