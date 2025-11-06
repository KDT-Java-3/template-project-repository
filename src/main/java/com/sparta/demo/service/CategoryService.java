package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.CategoryRepository;
import com.sparta.demo.service.dto.category.CategoryCreateDto;
import com.sparta.demo.service.dto.category.CategoryDto;
import com.sparta.demo.service.dto.category.CategoryUpdateDto;
import com.sparta.demo.service.mapper.CategoryServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryServiceMapper mapper;

    @Transactional
    public CategoryDto createCategory(CategoryCreateDto dto) {
        Category category;

        if (dto.getParentId() != null) {
            // 부모 카테고리 ID가 있는 경우 - 하위 카테고리 생성
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new ServiceException(
                            ServiceExceptionCode.PARENT_CATEGORY_NOT_FOUND, "ID: " + dto.getParentId()));
            category = Category.createWithParent(dto.getName(), dto.getDescription(), parent);
        } else {
            // 부모 카테고리 ID가 없는 경우 - 최상위 카테고리 생성
            category = Category.create(dto.getName(), dto.getDescription());
        }

        Category savedCategory = categoryRepository.save(category);
        return mapper.toDto(savedCategory);
    }

    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.CATEGORY_NOT_FOUND, "ID: " + id));
        return mapper.toDto(category);
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CategoryUpdateDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        ServiceExceptionCode.CATEGORY_NOT_FOUND, "ID: " + id));
        category.update(dto.getName(), dto.getDescription());
        return mapper.toDto(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ServiceException(
                    ServiceExceptionCode.CATEGORY_NOT_FOUND, "ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
