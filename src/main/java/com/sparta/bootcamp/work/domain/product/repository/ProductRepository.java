package com.sparta.bootcamp.work.domain.product.repository;

import com.sparta.bootcamp.work.domain.category.entity.Category;
import com.sparta.bootcamp.work.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    Optional<Product> findTopByCategoryAndNameAndPriceBetween(Category category, String name, BigDecimal priceStart, BigDecimal priceEnd);

}
