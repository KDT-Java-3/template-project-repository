package com.sparta.ecommerce.domain.product.service;

import com.sparta.ecommerce.domain.category.entity.Category;
import com.sparta.ecommerce.domain.category.repository.CategoryRepository;
import com.sparta.ecommerce.domain.product.dto.ProductCreateRequest;
import com.sparta.ecommerce.domain.product.dto.ProductReadRequest;
import com.sparta.ecommerce.domain.product.dto.ProductResponse;
import com.sparta.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.sparta.ecommerce.domain.product.entity.Product;
import com.sparta.ecommerce.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest dto) {
        Category category = (Category)this.categoryRepository.getReferenceById(dto.getCategoryId());
        Product product = Product.builder().name(dto.getName()).price(dto.getPrice()).stock(dto.getStock()).category(category).description(dto.getDescription()).build();
        Product res = (Product)this.productRepository.save(product);
        return ProductResponse.fromEntity(res);
    }

    @Transactional(
            readOnly = true
    )
    public List<ProductResponse> raedProducts(ProductReadRequest dto) {
        List<Product> products = this.productRepository.search(dto.getCategoryId(), dto.getMinPrice(), dto.getMaxPrice(), dto.getName());
        return products.stream().map(ProductResponse::fromEntity).toList();
    }

    @Transactional(
            readOnly = true
    )
    public ProductResponse readProduct(ProductReadRequest dto) {
        Product product = (Product)this.productRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public ProductResponse updateProduct(ProductUpdateRequest dto) {
        Category category = (Category)this.categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
        Product product = (Product)this.productRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        product.update(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock(), category);
        return ProductResponse.fromEntity(product);
    }

    @Generated
    public ProductService(final ProductRepository productRepository, final CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
}

