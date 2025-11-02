package com.sparta.demo1.repository;

import com.sparta.demo1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(Long id);

    List<Category> findAll();

    Category findByName(String name);
}
