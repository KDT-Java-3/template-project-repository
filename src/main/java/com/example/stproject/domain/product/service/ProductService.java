package com.example.stproject.domain.product.service;

import com.example.stproject.domain.category.entity.Category;
import com.example.stproject.domain.category.mapper.CategoryMapper;
import com.example.stproject.domain.category.repository.CategoryRepository;
import com.example.stproject.domain.product.dto.ProductCreateRequest;
import com.example.stproject.domain.product.dto.ProductResponse;
import com.example.stproject.domain.product.dto.ProductSearchCond;
import com.example.stproject.domain.product.dto.ProductUpdateRequest;
import com.example.stproject.domain.product.entity.Product;
import com.example.stproject.domain.product.entity.QProduct;
import com.example.stproject.domain.product.mapper.ProductMapper;
import com.example.stproject.domain.product.repository.ProductQueryRepository;
import com.example.stproject.domain.product.repository.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductQueryRepository productQueryRepository;

    @Transactional
    public Long create(ProductCreateRequest req) {
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "카테고리를 찾을 수 없습니다. id=" + req.getCategoryId()));

        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .stock(req.getStock())
                .category(category)
                .build();
        return productRepository.save(product).getId();
    }

    // 단건조회
    public ProductResponse getOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        log.debug("### 단건조회");
        log.debug(product.toString());
        return productMapper.toResponse(product);
    }

    // 다건조회
    public List<ProductResponse> getAll() {
        return productMapper.toResponseList(productRepository.findAll());
    }

    // 상품 수정
    @Transactional
    public Long update(ProductUpdateRequest req) {
        Product product = productRepository.findById(req.getId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID=" + req.getId()));
        productMapper.updateProductFromDto(req, product, categoryRepository);
        return product.getId();
    }

    public Page<ProductResponse> search(ProductSearchCond cond, Pageable pageable) {
        return productQueryRepository.search(cond, pageable)
                .map(productMapper::toResponse);
    }
}
