package com.sparta.demo.product.service;

import com.sparta.demo.category.domain.Category;
import com.sparta.demo.category.domain.CategoryRepository;
import com.sparta.demo.product.controller.request.ProductSearchCondition;
import com.sparta.demo.product.domain.Product;
import com.sparta.demo.product.domain.ProductRepository;
import com.sparta.demo.product.service.command.ProductSaveCommand;
import com.sparta.demo.product.service.command.ProductUpdateCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Long save(ProductSaveCommand command) {
        Category category = categoryRepository.getById(command.categoryId());
        Product product = command.toEntity(category);
        productRepository.save(product);
        return product.getId();
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(ProductSearchCondition condition) {
        return productRepository.findBySearchConditions(
                condition.categoryId(),
                condition.minPrice(),
                condition.maxPrice(),
                condition.keyword()
        );
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.getById(id);
    }

    public void update(ProductUpdateCommand command) {
        Product product = productRepository.getById(command.id());
        Category category = categoryRepository.getById(command.categoryId());
        product.update(
                command.name(),
                command.description(),
                command.price(),
                command.stock(),
                category
        );
    }
}
