package com.sparta.hodolee246.week01project.repository;

import com.sparta.hodolee246.week01project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
