package com.example.week01_project.repository;
import com.example.week01_project.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    long countByParent_Id(Long parentId);
}
