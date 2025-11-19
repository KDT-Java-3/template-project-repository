package io.depark.commerceservice.repository;

import io.depark.commerceservice.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundJpaRepository extends JpaRepository<Refund, Long> {
}
