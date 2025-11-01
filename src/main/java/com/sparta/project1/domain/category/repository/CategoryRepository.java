package com.sparta.project1.domain.category.repository;

import com.sparta.project1.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c join fetch c.parent")
    List<Category> findAllByFetch();
}
