package com.sparta.product.domain.product;

import com.sparta.product.domain.category.CategoryService;
import com.sparta.product.domain.category.dto.response.SearchResponse;
import com.sparta.product.domain.product.dto.request.RegisterRequest;
import com.sparta.product.domain.product.dto.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public RegisterResponse register(RegisterRequest request) {
        SearchResponse searchResponse = categoryService.searchCategoryById(request.getCategoryId());
        Product product = request.toEntity(searchResponse);
        Product saved = productRepository.save(product);
        return RegisterResponse.of(saved);
    }
}
