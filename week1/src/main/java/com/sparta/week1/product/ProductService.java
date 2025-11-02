package com.sparta.week1.product;

import com.sparta.week1.category.CategoryRepository;
import com.sparta.week1.dto.product.CreateProductDto;
import com.sparta.week1.entity.Category;
import com.sparta.week1.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Product createProduct(CreateProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category)
                .build();

        return repository.save(product);
    }

    public List<Product> getAllProducts(Long categoryId, Integer minPrice, Integer maxPrice, String keyword) {
        return repository.searchProducts(categoryId, minPrice, maxPrice, keyword);
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    @Transactional
    public Product updateProduct(Long id, CreateProductDto dto) {
        Product product = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (dto.getName() != null) {
            product.updateName(dto.getName());
        }
        if (dto.getPrice() != null) {
            product.updatePrice(dto.getPrice());
        }
        if (dto.getStock() != null) {
            product.updateStock(dto.getStock());
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
            product.changeCategory(category);
        }

        return product;
    }
}
