package com.sparta.commerce.management.repository;

import com.sparta.commerce.management.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RefundRepository extends JpaRepository<Refund, UUID> {

    List<Refund> findAllByPurchaseUserId(String purchaseUserId);
}
