package com.example.demo.service;


import com.example.demo.common.ServiceExceptionCode;
import com.example.demo.controller.dto.ProductRequestDto;
import com.example.demo.controller.dto.ProductResponseDto;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class ProductService {
    // 생성자 주입
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Repository
     * List<Product> findByName
     * boolean existsByName
     * List<Product> findByCategoryIdOrderByCreatedAtDesc
     * List<Product> findLowStockProducts
     */

    public List<ProductResponseDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getById(Long id) {
//        return productRepository.findById(id).orElseThrow();
        return ProductResponseDto.fromEntity(
                productRepository.findById(id)
                        .orElseThrow(
                                () -> new ServiceException(
                                        ServiceExceptionCode.NOT_FOUND_PRODUCT.getMessage()
                                )
                        )
        );
    }

    public ProductResponseDto create(ProductRequestDto request) {
        // 중복체크
        if (productRepository.existsByName(request.getName())) {
            throw new RuntimeException("product already have");
        }
        // DTO -> Entity
        // Entity -> productRepository
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다"));

        return ProductResponseDto.fromEntity(productRepository.save(request.toEntity(category)));
    }


}
