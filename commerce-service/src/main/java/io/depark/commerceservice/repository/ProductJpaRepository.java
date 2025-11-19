package io.depark.commerceservice.repository;

import io.depark.commerceservice.entity.Category;
import io.depark.commerceservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Boolean existsByCategory(Category category);
}
