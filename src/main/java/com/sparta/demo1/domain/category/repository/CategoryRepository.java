package com.sparta.demo1.domain.category.repository;

import com.sparta.demo1.domain.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.productList")
    List<CategoryEntity> findAllWithProducts();
}
