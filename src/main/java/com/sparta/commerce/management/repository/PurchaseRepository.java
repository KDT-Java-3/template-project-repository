package com.sparta.commerce.management.repository;

import com.sparta.commerce.management.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    List<Purchase> findAllByUserId(UUID userId);
}
