package com.sparta.week1.category;

import com.sparta.week1.dto.category.CreateCategoryDto;
import com.sparta.week1.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;


    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories(String name, String description) {
        return repository.searchCategories(name, description);
    }

    @Transactional
    public Category createCategory(CreateCategoryDto dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .build();

        return repository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, CreateCategoryDto dto) {
        Category category = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        if (dto.getName() != null) {
            category.updateName(dto.getName());
        }

        if (dto.getDescription() != null) {
            category.updateDescription(dto.getDescription());
        }

        return category;
    }
}
