package com.pepponechoi.project.domain.category.repository;

import com.pepponechoi.project.domain.category.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    @Query("SELECT c FROM Category c join fetch c.products")
    List<Category> findAllFetch();
    @Query("SELECT c FROM Category c join fetch c.products WHERE c.id = :id")
    Optional<Category> findByIdFetch(Long id);
}