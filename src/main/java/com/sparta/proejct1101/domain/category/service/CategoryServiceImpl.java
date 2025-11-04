package com.sparta.proejct1101.domain.category.service;

import com.sparta.proejct1101.domain.category.dto.request.CategoryReq;
import com.sparta.proejct1101.domain.category.dto.response.CategoryRes;
import com.sparta.proejct1101.domain.category.entity.Category;
import com.sparta.proejct1101.domain.category.repository.CategoryRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRespository categoryRespository;

    @Override
    public CategoryRes createCategory(CategoryReq req) {
        Category category = Category.builder()
                .name(req.name())
                .description(req.description())
                .build();

        if(req.parentId() != null){
            Category parent = categoryRespository.findById(req.parentId()).orElseThrow();
            category.setParent(parent);
        }
        return CategoryRes.from(categoryRespository.save(category));
    }

    @Override
    public List<CategoryRes> getCategory() {
        List<Category> categories = categoryRespository.findAll();
        return categories.stream().map(CategoryRes::from).toList();
    }

    @Override
    @Transactional
    public CategoryRes updateCategory(CategoryReq req) {
        Category category = categoryRespository.findById(req.id()).orElseThrow();
        category.update(req.name(), req.description());

        return CategoryRes.from(category);
    }
}
