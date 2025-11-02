package com.example.stproject.domain.category.service;

import com.example.stproject.domain.category.dto.CategoryCreateRequest;
import com.example.stproject.domain.category.dto.CategoryResponse;
import com.example.stproject.domain.category.dto.CategoryUpdateRequest;
import com.example.stproject.domain.category.entity.Category;
import com.example.stproject.domain.category.mapper.CategoryMapper;
import com.example.stproject.domain.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /** 등록 */
    @Transactional
    public Long create(CategoryCreateRequest req) {
        if (categoryRepository.existsByName(req.getName())) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다: " + req.getName());
        }
        Category saved = categoryRepository.save(categoryMapper.toEntity(req));
        return saved.getId();
    }

    /** 전체 조회 */
    // @Transactional(readOnly = true)
    public List<CategoryResponse> getAll(boolean includeProducts) {
        List<Category> categories;

        if (includeProducts) {
            categories = categoryRepository.findAllWithProducts(); // fetch join
            return categories.stream()
                    .map(categoryMapper::toResponse)
                    .toList();
        } else {
            categories = categoryRepository.findAll(); // products 건드리지 않음
            return categories.stream()
                    .map(categoryMapper::toResponseWithoutProducts)
                    .toList();
        }
    }

    /** 수정 */
    @Transactional
    public Long update(CategoryUpdateRequest req) {
        Category category = categoryRepository.findById(req.getId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. id=" + req.getId()));
        categoryMapper.updateEntityFromDto(req, category); // null 필드 무시하고 덮어쓰기
        return category.getId();
    }
}
