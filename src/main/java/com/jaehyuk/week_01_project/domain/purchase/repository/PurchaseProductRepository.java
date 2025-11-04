package com.jaehyuk.week_01_project.domain.purchase.repository;

import com.jaehyuk.week_01_project.domain.purchase.entity.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {
}
