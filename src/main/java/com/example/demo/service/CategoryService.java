package com.example.demo.service;

import com.example.demo.common.ServiceException;
import com.example.demo.common.ServiceExceptionCode;
import com.example.demo.controller.dto.CategoryResponseDto;
import com.example.demo.controller.dto.ProductResponseDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.dto.CategoryServiceInputDto;
import com.example.demo.service.dto.ProductServiceInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAll() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }

//    @Transactional
//    public CategoryResponseDto create(CategoryServiceInputDto input) {
//
//        Category parentCategoryId = findCategory(input.getParent().getId());
//
//        Category category = Category.builder()
//                .name(input.getName())
//                .parent(parentCategoryId)
//                .build();
//
//        return categoryMapper.toResponse(categoryRepository.save(category));
//    }

    private Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionCode.NOT_FOUND_CATEGORY));
    }
}
