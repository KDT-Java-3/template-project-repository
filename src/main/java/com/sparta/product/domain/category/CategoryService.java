package com.sparta.product.domain.category;

import com.sparta.product.domain.category.dto.request.RegisterRequest;
import com.sparta.product.domain.category.dto.request.UpdateRequest;
import com.sparta.product.domain.category.dto.response.RegisterResponse;
import com.sparta.product.domain.category.dto.response.SearchAllResponse;
import com.sparta.product.domain.category.dto.response.SearchResponse;
import com.sparta.product.domain.category.dto.response.UpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public RegisterResponse register(RegisterRequest registerRequest) {
        Category category = registerRequest.toEntity();
        Category saved = categoryRepository.save(category);
        return RegisterResponse.of(saved);
    }

    @Transactional(readOnly = true)
    public SearchAllResponse searchCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        return SearchAllResponse.of(categoryList);
    }

    @Transactional(readOnly = true)
    public SearchResponse searchCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("없는 카테고리"));
        return SearchResponse.of(category);
    }

    @Transactional
    public UpdateResponse update(Long id, UpdateRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("없는 카테고리"));
        category.update(request);
        return UpdateResponse.of(category);
    }
}
