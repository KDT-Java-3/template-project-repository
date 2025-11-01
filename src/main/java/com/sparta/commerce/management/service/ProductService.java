package com.sparta.commerce.management.service;

import com.sparta.commerce.management.dto.request.product.ProductRequest;
import com.sparta.commerce.management.dto.request.product.ProductSearchRequest;
import com.sparta.commerce.management.dto.response.product.ProductResponse;
import com.sparta.commerce.management.entity.Category;
import com.sparta.commerce.management.entity.Product;
import com.sparta.commerce.management.repository.CategoryRepository;
import com.sparta.commerce.management.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    //상품 등록 API
    public ProductResponse save(ProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow( () -> new NotFoundException("카테고리가 없습니다."));
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        Product save = productRepository.save(product);

        return ProductResponse.getProduct(save);
    }

    //상품 조회 API

        //단일 상품
        public Product findById(UUID id){
            return productRepository.findById(id).orElse(null);
        }
        //전체 상품
        public List<Product> findAll(){
            return productRepository.findAll();
        }
        //검색 및 필터링
        public List<ProductResponse> searchProducts(ProductSearchRequest request){
            List<Product> productList = productRepository.searchProducts(request.getCategoryId(), request.getMinPrice(), request.getMaxPrice(), request.getKeyWord());

            return ProductResponse.getProductList(productList);
        }

    //상품 수정 API
    public ProductResponse update(UUID id, ProductRequest request) {
        //수정할 상품 조회
        Product product = productRepository.findById(id).orElseThrow(()-> new NotFoundException("해당하는 상품이 없습니다"));

        //상품 카테고리  조회
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("카테고리가 없습니다"));

        //상품 수정
        product.update(request.getName(), request.getDescription(), request.getPrice(), request.getStock(), category);

        return ProductResponse.getProduct(product);
    }
}
