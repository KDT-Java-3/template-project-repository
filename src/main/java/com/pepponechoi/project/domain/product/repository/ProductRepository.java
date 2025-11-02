package com.pepponechoi.project.domain.product.repository;

import com.pepponechoi.project.domain.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory_Id(Long categoryId);
    List<Product> findAllByNameContains(String name);
    List<Product> findAllByDescriptionContains(String description);
    List<Product> findAllByPriceBetween(Long from, Long to);
}
