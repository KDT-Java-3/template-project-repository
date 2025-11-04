package com.sparta.demo1.domain.service;

import com.sparta.demo1.domain.dto.request.ProductCreateRequest;
import com.sparta.demo1.domain.dto.request.ProductSearchRequest;
import com.sparta.demo1.domain.dto.request.ProductUpdateRequest;
import com.sparta.demo1.domain.dto.response.ProductResponse;
import com.sparta.demo1.domain.entity.Category;
import com.sparta.demo1.domain.entity.Product;
import com.sparta.demo1.domain.repository.CategoryRepository;
import com.sparta.demo1.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Transactional
  public ProductResponse createProduct(ProductCreateRequest request) {
    // 카테고리 조회
    Category category = null;
    if (request.getCategoryId() != null) {
      category = categoryRepository.findById(request.getCategoryId())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }

    // 상품 생성
    Product product = Product.builder()
        .category(category)
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .stock(request.getStock())
        .build();

    Product savedProduct = productRepository.save(product);
    return ProductResponse.from(savedProduct);
  }

  public ProductResponse getProduct(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    return ProductResponse.from(product);
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(ProductResponse::from)
        .collect(Collectors.toList());
  }

  public List<ProductResponse> searchProducts(ProductSearchRequest request) {
    List<Product> products;

    // 카테고리 기반 검색
    if (request.getCategoryId() != null) {
      Category category = categoryRepository.findById(request.getCategoryId())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

      products = productRepository.findByCategoryAndNameContainingAndPriceBetween(
          category,
          request.getKeyword() != null ? request.getKeyword() : "",
          request.getMinPrice() != null ? request.getMinPrice() : 0L,
          request.getMaxPrice() != null ? request.getMaxPrice() : Long.MAX_VALUE
      );
    }
    // 키워드만 검색
    else if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
      products = productRepository.findByNameContaining(request.getKeyword());
    }
    // 가격 범위만 검색
    else if (request.getMinPrice() != null || request.getMaxPrice() != null) {
      products = productRepository.findByPriceBetween(
          request.getMinPrice() != null ? request.getMinPrice() : 0L,
          request.getMaxPrice() != null ? request.getMaxPrice() : Long.MAX_VALUE
      );
    }
    // 조건 없으면 전체 조회
    else {
      products = productRepository.findAll();
    }

    return products.stream()
        .map(ProductResponse::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

    // 카테고리 변경
    if (request.getCategoryId() != null) {
      Category category = categoryRepository.findById(request.getCategoryId())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
      product.updateCategory(category);
    }

    // 나머지 필드 업데이트
    product.updateProductInfo(
        request.getName(),
        request.getDescription(),
        request.getPrice(),
        request.getStock()
    );

    return ProductResponse.from(product);
  }

  @Transactional
  public void deleteProduct(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    productRepository.delete(product);
  }
}
