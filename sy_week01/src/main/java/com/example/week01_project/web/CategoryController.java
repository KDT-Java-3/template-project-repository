package com.example.week01_project.web;

import com.example.week01_project.dto.category.CategoryRequest;
import com.example.week01_project.dto.category.CategoryResponse;
import com.example.week01_project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;
    public CategoryController(CategoryService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid CategoryRequest req){ return service.create(req); }

    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable Long id){ return service.get(id); }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid CategoryRequest req){ service.update(id, req); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){ service.delete(id); }

    // ✅ [보충] 카테고리별 최다 판매 Top10은 별도 통계 API로 확장 권장
}

