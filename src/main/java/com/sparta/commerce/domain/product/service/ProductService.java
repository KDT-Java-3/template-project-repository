package com.sparta.commerce.domain.product.service;

import com.sparta.commerce.entity.Category;
import com.sparta.commerce.entity.Product;
import com.sparta.commerce.exception.NotFoundProductException;
import com.sparta.commerce.domain.product.dto.ModifyProductDto;
import com.sparta.commerce.domain.product.dto.ProductDto;
import com.sparta.commerce.domain.product.dto.SaveProductDto;
import com.sparta.commerce.domain.product.repository.ProductRepository;
import com.sparta.commerce.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductDto findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(NotFoundProductException::new);

        return ProductDto.of(product);
    }

    @Transactional
    public Long saveProduct(SaveProductDto dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(NotFoundProductException::new);

        Product product = Product.builder()
                .name(dto.name())
                .description(dto.description())
                .category(category)
                .price(dto.price())
                .stock(dto.stock())
                .build();

        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public void modifyProduct(
            Long id,
            ModifyProductDto dto
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(NotFoundProductException::new);

        product.changeProduct(dto);
    }
}
