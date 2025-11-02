package com.sparta.demo1.service;

import com.sparta.demo1.entity.Category;
import com.sparta.demo1.entity.Product;
import com.sparta.demo1.repository.CategoryRepository;
import com.sparta.demo1.repository.ProductRepository;
import com.sparta.demo1.service.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    // 조건 조회
    public List<Product> selectProducts(String category, String name, Integer minPrice, Integer maxPrice) {
        return productRepository.selectProducts(category, name, minPrice, maxPrice);
    }

    // 조건 조회
    public void updateProduct( ProductRequest productRequest) {


        Product product = productRepository.findByName(productRequest.getName());

        // null이 아닌 것만 업데이트
        if (productRequest.getName() != null) {
            product.setName(productRequest.getName());
        }
        if (productRequest.getDescription() != null) {
            product.setDescription(productRequest.getDescription());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        if (productRequest.getStock() != null) {
            product.setStock(productRequest.getStock());
        }
        if (productRequest.getCategory() != null) {
            // Category 객체 생성 또는 조회
            product.setCategory(productRequest.getCategory());
        }

        // 업데이트된 product 저장
        productRepository.save(product);
    }

    public Product createProduct(ProductRequest productRequest) {



        Category category = categoryRepository.findById(productRequest.getCategory().getId());
        if(category == null){
            throw new RuntimeException("카테고리를 찾을 수 없습니다");
        };

        if (productRequest.getStock() == null) {
            throw new RuntimeException("재고를 입력해주세요");
        }

        if (productRequest.getName() == null) {
            throw new RuntimeException("상품명을 입력해주세요");
        }

        if (productRequest.getPrice() == null) {
            throw new RuntimeException("가격을 입력해주세요");
        }

        if (productRepository.existsByName((productRequest.getName()))) {
            throw new RuntimeException("이미 존재하는 상품명입니다");
        }

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .category(category)
                .build();

        return productRepository.save(product);
    }
}
