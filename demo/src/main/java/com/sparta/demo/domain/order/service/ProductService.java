package com.sparta.demo.domain.order.service;

import com.sparta.demo.domain.order.dto.request.CreateProductRequest;
import com.sparta.demo.domain.order.dto.request.UpdateProductRequest;
import com.sparta.demo.domain.order.dto.response.ProductResponse;
import com.sparta.demo.domain.order.entity.Product;
import com.sparta.demo.domain.order.repository.CategoryRepository;
import com.sparta.demo.domain.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductResponse createProduct(CreateProductRequest request) throws Exception {
        Product product = productRepository.getProductByNameAndCategoryId(request.getName(), request.getCategoryId());
        if(product != null) {
            throw new Exception("동일한 상품이 존재합니다.");
        }

        Product saved = productRepository.save(Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(categoryRepository.getReferenceById(request.getCategoryId()))
                .build());
        return ProductResponse.buildFromEntity(saved);
    }

    // 전체 조회
    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(ProductResponse::buildFromEntity)
                .toList();
    }

    // 단건 조회
    public ProductResponse getProductById(Long productId){
        Product product = productRepository.findById(productId).orElse(null);
        return ProductResponse.buildFromEntity(product);
    }

    // 수정
    public ProductResponse updateProduct(UpdateProductRequest request) throws Exception {
        Product product = productRepository.findById(request.getId())
                .orElseThrow(()-> new Exception("해당 상품이 존재하지 않습니다."));
        product.updateValues(request);
        product.changeCategory(categoryRepository.getReferenceById(request.getCategoryId()));
        productRepository.save(product);
        return ProductResponse.buildFromEntity(product);
    }

    // 검색
    public List<ProductResponse> searchProducts(Long categoryId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, String keyword) {
        List<Product> products = productRepository.searchProducts(categoryId, minPrice, maxPrice, keyword);
        return products.stream()
                .map(ProductResponse::buildFromEntity)
                .toList();
    }

}
