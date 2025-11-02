package com.sparta.week1.category;

import com.sparta.week1.dto.category.CreateCategoryDto;
import com.sparta.week1.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;

    /**
     * 카테고리 조회
     */
    @GetMapping
    public ResponseEntity<List<Category>> getCategories(@Param("name") String name, @Param("description") String description) {
        List<Category> category = service.getAllCategories(name, description);
        return ResponseEntity.ok().body(category);
    }

    /**
     * 카테고리 등록
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryDto dto) {
        Category category = service.createCategory(dto);
        return ResponseEntity.ok().body(category);
    }

    /**
     * 카테고리 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateProduct(@PathVariable Long id, @RequestBody CreateCategoryDto dto) {
        Category category = service.updateCategory(id, dto);
        return ResponseEntity.ok().body(category);
    }

}
