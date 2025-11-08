package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.DataReferencedException;
import com.sprata.sparta_ecommerce.controller.exception.DuplicationException;
import com.sprata.sparta_ecommerce.dto.CategoryDetailResponseDto;
import com.sprata.sparta_ecommerce.dto.CategoryRequestDto;
import com.sprata.sparta_ecommerce.dto.CategoryResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.findByName(categoryRequestDto.getName()).isPresent()) {
            throw new DuplicationException("이미 존재하는 카테고리입니다. : "+ categoryRequestDto.getName() );
        }

        /** 카테고리 부모 지정
         * parent_id = 0L 일시, 최상위 부모
         * */
        Category parentCategory = null;
        if(categoryRequestDto.getParent_id() != 0L){
            parentCategory = categoryRepository.findById(categoryRequestDto.getParent_id())
                    .orElseThrow(() -> new DataNotFoundException("상위 카테고리를 찾을 수 없습니다."));
        }

        Category category = Category.builder()
                        .name(categoryRequestDto.getName())
                        .description(categoryRequestDto.getDescription())
                        .parentCategory(parentCategory)
                        .build();

        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponseDto(savedCategory, parentCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDetailResponseDto> getAllCategories(PageDto pageDto) {




        return categoryRepository.findListPaging(pageDto).stream()
                .map(CategoryDetailResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("해당 카테고리를 찾을 수 없습니다."));

        categoryRepository.findByName(categoryRequestDto.getName()).ifPresent(c -> {
            if (!c.getId().equals(categoryId)) {
                throw new DuplicationException("이미 존재하는 카테고리명입니다.");
            }
        });

        /** 카테고리 부모 지정
         * parent_id = 0L 일시, 최상위 부모
         * */
        Category parentCategory = null;
        if(categoryRequestDto.getParent_id() != 0L){
            parentCategory = categoryRepository.findById(categoryRequestDto.getParent_id())
                    .orElseThrow(() -> new DataNotFoundException("상위 카테고리를 찾을 수 없습니다."));
        }

        category.update(categoryRequestDto.getName(), categoryRequestDto.getDescription(), parentCategory);
        return new CategoryResponseDto(category, null);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("해당 카테고리를 찾을 수 없습니다."));

        if(!category.getSubCategories().isEmpty()){
            throw new DataReferencedException("하위 카테고리가 존재합니다.");
        }

        if(!category.getProducts().isEmpty()){
            throw new DataReferencedException("연관된 상품이 존재합니다.");
        }

        categoryRepository.delete(category);
    }
}
