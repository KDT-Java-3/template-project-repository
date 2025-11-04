package com.jaehyuk.week_01_project.domain.category.repository;

import com.jaehyuk.week_01_project.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
