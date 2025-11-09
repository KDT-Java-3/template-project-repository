package com.sprata.sparta_ecommerce.repository;

import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    Optional<Category> findByName(String name);

    List<Category> findByParentCategoryIsNull();

    // 특정 depth까지만 조회하는 네이티브 쿼리
    @Query(value = """
        WITH RECURSIVE category_tree AS (
            SELECT 
                id,
                name,
                description,
                parent_id,
                0 AS depth
            FROM category
            WHERE parent_id IS NULL
            
            UNION ALL
            
            SELECT 
                c.id,
                c.name,
                c.description,
                c.parent_id,
                ct.depth + 1
            FROM category c
            INNER JOIN category_tree ct ON c.parent_id = ct.id
        )
        SELECT * FROM category_tree ORDER BY depth, id
        """, nativeQuery = true)
    List<Object[]> findCategoryTreeWithDepth();
}
