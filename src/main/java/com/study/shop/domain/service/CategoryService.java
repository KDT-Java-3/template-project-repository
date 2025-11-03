package com.study.shop.domain.service;

import com.study.shop.domain.dto.CategoryDto;
import com.study.shop.domain.entity.Category;
import com.study.shop.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = false)
    public CategoryDto.Resp createCategory(CategoryDto.CreateCategory request) {

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return CategoryDto.Resp.of(savedCategory, false);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto.Resp> findAll(boolean includeProducts) {
        if (includeProducts) {
            return categoryRepository.findAllWithProductsFetchJoin()
                    .stream().map(c -> CategoryDto.Resp.of(c, true)).toList();
        } else {
            return categoryRepository.findAll()
                    .stream().map(c -> CategoryDto.Resp.of(c, false)).toList();
        }
    }

    @Transactional(readOnly = false)
    public CategoryDto.Resp update(Long id, CategoryDto.UpdateCategory req) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 없습니다. id=" + id));

        if (req.getName() != null && !req.getName().isBlank()) {
            c.setName(req.getName());
        }
        if (req.getDescription() != null) {
            c.setDescription(req.getDescription());
        }
        // @Transactional로 더티체킹 반영
        return CategoryDto.Resp.of(c, false);
    }
}
