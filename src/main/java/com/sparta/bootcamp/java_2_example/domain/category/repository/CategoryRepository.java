package com.sparta.bootcamp.java_2_example.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.bootcamp.java_2_example.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
