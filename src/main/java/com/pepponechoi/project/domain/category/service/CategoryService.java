package com.pepponechoi.project.domain.category.service;

import com.pepponechoi.project.domain.category.dto.request.CategoryCreateRequest;
import com.pepponechoi.project.domain.category.dto.request.CategoryUpdateRequest;
import com.pepponechoi.project.domain.category.dto.response.CategoryResponse;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

public interface CategoryService {
    CategoryResponse create(CategoryCreateRequest request);
    List<CategoryResponse> getAll();
    CategoryResponse getById(Long id);
    void update(Long id, CategoryUpdateRequest request);
    void delete(Long id);
}
