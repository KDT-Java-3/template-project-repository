package com.sparta.restful_1week.domain.category.service;

import com.sparta.restful_1week.domain.category.dto.CategoryInDTO;
import com.sparta.restful_1week.domain.category.dto.CategoryOutDTO;
import com.sparta.restful_1week.domain.category.entity.Category;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryService {

    private final Map<Long, Category> categoryMap = new HashMap<>();

    public CategoryOutDTO createCategory(CategoryInDTO categoryInDTO) {

        // inDto -> entity
        Category category = new Category(categoryInDTO);

        // category max id chk
        Long maxId = categoryMap.size() > 0 ? Collections.max(categoryMap.keySet()) + 1 : 1;
        category.setId(maxId);

        // db 저장
        categoryMap.put(category.getId(), category);

        // entity -> outDto
        CategoryOutDTO categoryOutDTO = new CategoryOutDTO(category);

        return categoryOutDTO;
    }

    public List<CategoryOutDTO> getCategories() {
        // Map to list
        List<CategoryOutDTO> categoryOutDTOList = categoryMap.values().stream()
                .map(CategoryOutDTO::new).toList();

        return categoryOutDTOList;
    }

    public CategoryOutDTO updateCategory(Long id, CategoryInDTO categoryInDTO) {
        // 해당 메모가 DB에 존재하는지 확인
        if(categoryMap.containsKey(id)) {
            // 해당 ID로 카테고리 가져오기
            Category category = categoryMap.get(id);

            // 메모 수정
            category.updateCategory(categoryInDTO);

            // entity -> outDto
            CategoryOutDTO categoryOutDTO = new CategoryOutDTO(category);

            return categoryOutDTO;

        } else {
            throw new IllegalArgumentException("선택한 카테고리 값은 존재하지 않습니다.");
        }
    }
}
