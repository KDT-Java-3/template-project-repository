package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.dto.category.CategoryCreateDto;
import com.sparta.demo.dto.category.CategoryDto;
import com.sparta.demo.dto.category.CategoryUpdateDto;
import com.sparta.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(CategoryCreateDto dto) {
        Category category = Category.create(dto.getName(), dto.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return CategoryDto.from(savedCategory);
    }

    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: " + id));
        return CategoryDto.from(category);
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CategoryUpdateDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: " + id));
        category.update(dto.getName(), dto.getDescription());
        return CategoryDto.from(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다. ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
