package com.sparta.demo.category.service;

import com.sparta.demo.category.domain.Category;
import com.sparta.demo.category.domain.CategoryRepository;
import com.sparta.demo.category.service.command.CategorySaveCommand;
import com.sparta.demo.category.service.command.CategoryUpdateCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long save(CategorySaveCommand command) {
        Category category = command.toEntity();
        categoryRepository.save(category);
        return category.getId();
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void update(CategoryUpdateCommand command) {
        Category category = categoryRepository.getById(command.id());
        category.update(command.name(), command.description());
    }
}
