package com.sparta.ecommerce.product.application;

import static com.sparta.ecommerce.product.application.dto.ProductDto.*;

import com.sparta.ecommerce.category.domain.Category;
import com.sparta.ecommerce.category.infrastructure.CategoryJpaRepository;
import com.sparta.ecommerce.product.domain.Product;
import com.sparta.ecommerce.product.infrastructure.ProductJpaRepository;
import com.sparta.ecommerce.product.infrastructure.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest createRequest) {
        Category category = categoryJpaRepository.findById(createRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = createRequest.toEntity(category);

        return ProductResponse.fromEntity(productJpaRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest updateRequest) {
        Product product = productJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category newCategory = categoryJpaRepository.findById(updateRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.update(
                updateRequest.getName(),
                updateRequest.getDescription(),
                updateRequest.getPrice(),
                updateRequest.getStock(),
                newCategory
        );

        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts(ProductSearchRequest searchRequest) {
        Specification<Product> spec = ProductSpecification.withFilters(
                searchRequest.getCategoryId(),
                searchRequest.getName(),
                searchRequest.getMinPrice(),
                searchRequest.getMaxPrice()
        );

        List<Product> products = productJpaRepository.findAll(spec);

        return products.stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }
}
