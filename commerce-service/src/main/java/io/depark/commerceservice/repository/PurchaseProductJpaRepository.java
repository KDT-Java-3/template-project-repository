package io.depark.commerceservice.repository;

import io.depark.commerceservice.entity.Product;
import io.depark.commerceservice.entity.PurchaseProduct;
import io.depark.commerceservice.entity.enums.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseProductJpaRepository extends JpaRepository<PurchaseProduct, Long> {

    List<PurchaseProduct> findByProduct(Product product);
    Boolean existsByProduct_IdAndPurchase_Status(Long id, PurchaseStatus purchaseStatus);
}
