package com.sparta.project1.domain.category.repository;

import com.sparta.project1.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c left join fetch c.parent")
    List<Category> findAllByFetch();

    @Query(value = """
                with recursive cte as (
                    select
                        id, name, description, parent_id, created_at, updated_at
                    from category where id = 1
                    union all
                    select
                        c.id, c.name, c.description, c.parent_id, c.created_at, c.updated_at
                    from category c left join cte on cte.id = c.parent_id
                    where c.parent_id = cte.id
                )
                select id, name, description, parent_id, created_at, updated_at
                from cte
            """, nativeQuery = true)
    List<Category> findAllByParentId(@Param("parentId") Long parentId);
}
