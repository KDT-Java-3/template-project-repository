package com.wodydtns.commerce.domain.product.repository;

import com.wodydtns.commerce.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.id = :id")
        Optional<Product> findByIdWithCategory(@Param("id") Long id);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.name LIKE %:name%")
        List<Product> findByNameContaining(@Param("name") String name);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.categoryId = :categoryId")
        List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.price BETWEEN :minPrice AND :maxPrice")
        List<Product> findByPriceBetween(@Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.price >= :minPrice")
        List<Product> findByPriceGreaterThanEqual(@Param("minPrice") Integer minPrice);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.price <= :maxPrice")
        List<Product> findByPriceLessThanEqual(@Param("maxPrice") Integer maxPrice);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.name LIKE %:name% AND p.categoryId = :categoryId")
        List<Product> findByNameContainingAndCategoryId(@Param("name") String name,
                        @Param("categoryId") Long categoryId);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.name LIKE %:name% AND p.price BETWEEN :minPrice AND :maxPrice")
        List<Product> findByNameContainingAndPriceBetween(@Param("name") String name,
                        @Param("minPrice") Integer minPrice,
                        @Param("maxPrice") Integer maxPrice);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.categoryId = :categoryId AND p.price BETWEEN :minPrice AND :maxPrice")
        List<Product> findByCategoryIdAndPriceBetween(@Param("categoryId") Long categoryId,
                        @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.name LIKE %:name% AND p.categoryId = :categoryId AND p.price BETWEEN :minPrice AND :maxPrice")
        List<Product> findByNameContainingAndCategoryIdAndPriceBetween(@Param("name") String name,
                        @Param("categoryId") Long categoryId, @Param("minPrice") Integer minPrice,
                        @Param("maxPrice") Integer maxPrice);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category")
        List<Product> findAllWithCategory();
}
