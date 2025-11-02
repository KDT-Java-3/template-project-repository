package com.sparta.week1.category;

import com.sparta.week1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
        select c from Category c
        where (:name is null or c.name = :name)
        and (:description is null or c.description like %:description%)
""")
    List<Category> searchCategories(
            @Param("name") String name,
            @Param("description") String description
    );
}
