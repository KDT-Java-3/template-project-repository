package com.sparta.work.category.service;

import com.sparta.work.category.domain.Category;
import com.sparta.work.category.domain.CategoryRepository;
import com.sparta.work.category.dto.request.RequestCreateCategoryDto;
import com.sparta.work.category.dto.request.RequestUpdateCategoryDto;
import com.sparta.work.category.dto.response.ResponseCategoryDto;
import com.sparta.work.category.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<ResponseCategoryDto> findAllCategory(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(categoryMapper::toResponseDto).toList();
    }

    public ResponseCategoryDto findCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        return categoryMapper.toResponseDto(category);
    }

    public Long createCategory(RequestCreateCategoryDto dto) {
        Category category = categoryMapper.toEntity(dto);

        return categoryRepository.save(category).getId();
    }

    @Transactional
    public Long updateCategory(Long id, RequestUpdateCategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.update(dto.getName(), dto.getDescription());

        return category.getId();
    }

}
