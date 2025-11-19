package io.depark.commerceservice.repository;

import io.depark.commerceservice.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseJpaRepository extends JpaRepository<Purchase, Long> {
}
