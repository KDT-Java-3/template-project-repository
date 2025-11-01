package com.sparta.commerce.management.repository;

import com.sparta.commerce.management.entity.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, UUID> {
}
