package com.example.project_01.product;

import com.example.project_01.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReporsitory extends JpaRepository<ProductEntity, Integer> {
//    ProductGetRequest findById(String categoryId);

    @Override
    @Query("SELECT p FROM PRODUCT p JOIN FETCH p.category WHERE p.seq = :productId")
    Optional<ProductEntity> findById(Integer productId);

    @Override
    ProductEntity save(ProductEntity entity);



}

