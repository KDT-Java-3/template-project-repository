package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.DataReferencedException;
import com.sprata.sparta_ecommerce.controller.exception.DuplicationException;
import com.sprata.sparta_ecommerce.dto.CategoryDetailResponseDto;
import com.sprata.sparta_ecommerce.dto.CategoryRequestDto;
import com.sprata.sparta_ecommerce.dto.CategoryResponseDto;
import com.sprata.sparta_ecommerce.dto.CategoryTreeResponseDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.repository.projection.CategorySalesProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

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
    public List<CategoryDetailResponseDto> getTop10SalesCategories() {

        List<CategorySalesProjection> top10SalesCategory = categoryRepository.findTop10SalesCategory();

        return top10SalesCategory.stream()
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

    @Override
    @Transactional(readOnly = true)
    public List<CategoryTreeResponseDto> getCategoryTree() {
        /* 1. 재귀 조회 */
//        List<Category> rootCategories = categoryRepository.findByParentCategoryIsNull();
//        List<CategoryTreeResponseDto> collect = rootCategories.stream()
//                .map(CategoryTreeResponseDto::new)
//                .collect(Collectors.toList());
//
        /* 2. findAll() */
        // 1. 모든 카테고리를 평면적으로 조회 (쿼리 1번)
        List<Category> allCategories = categoryRepository.findAll();

        // 2. parent_id를 키로 하는 Map 생성
        Map<Long, List<Category>> parentChildMap = allCategories.stream()
                .filter(c -> c.getParentCategory() != null)
                .collect(Collectors.groupingBy(c -> c.getParentCategory().getId()));

        // 3. 최상위 카테고리 필터링
        List<Category> rootCategories = allCategories.stream()
                .filter(c -> c.getParentCategory() == null)
                .collect(Collectors.toList());

        // 4. DTO 변환 (Map 활용으로 지연 로딩 없음)
        List<CategoryTreeResponseDto> collect = rootCategories.stream()
                .map(root -> buildCategoryTree(root, parentChildMap))
                .collect(Collectors.toList());

        return collect;
    }


    private CategoryTreeResponseDto buildCategoryTree(
            Category category,
            Map<Long, List<Category>> parentChildMap) {

        CategoryTreeResponseDto dto = new CategoryTreeResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        // Map에서 자식 카테고리 가져오기 (DB 쿼리 없음!)
        List<Category> children = parentChildMap.getOrDefault(
                category.getId(),
                Collections.emptyList()
        );

        dto.setChildren(
                children.stream()
                        .map(child -> buildCategoryTree(child, parentChildMap))
                        .collect(Collectors.toList())
        );

        return dto;
    }

}
