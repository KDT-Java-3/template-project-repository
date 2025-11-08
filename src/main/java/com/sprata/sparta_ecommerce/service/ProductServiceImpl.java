package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.DataReferencedException;
import com.sprata.sparta_ecommerce.controller.exception.DuplicationException;
import com.sprata.sparta_ecommerce.dto.ProductResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.service.dto.ProductServiceInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public ProductResponseDto addProduct(ProductServiceInputDto inputDto) {
        Category category = categoryRepository.findById(inputDto.getCategory_id())
                .orElseThrow(() -> new DataNotFoundException("해당 카테고리를 찾을 수 없습니다."));

        if(productRepository.findByProductName(inputDto.getName()).isPresent()){
            throw new DuplicationException(inputDto.getName() + " 중복된 상품명 존재합니다.");
        }

        Product product = Product.builder()
                .name(inputDto.getName())
                .description(inputDto.getDescription())
                .price(inputDto.getPrice())
                .stock(inputDto.getStock())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다."));
        return new ProductResponseDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts(SearchProductDto searchProductDto, PageDto pageDto) {

        return productRepository.searchProducts(searchProductDto, pageDto).stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductServiceInputDto updateDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다."));

        Category category = categoryRepository.findById(updateDto.getCategory_id())
                .orElseThrow(() -> new DataNotFoundException("해당 카테고리를 찾을 수 없습니다."));


        if(productRepository.findByProductName(updateDto.getName()).isPresent()){
            Product findProduct = productRepository.findByProductName(updateDto.getName()).get();
            if(!product.equals(findProduct)){
                throw new DuplicationException(updateDto.getName() + " 중복된 상품명 존재합니다.");
            }
        }

        product.update(updateDto.getName()
                ,updateDto.getDescription()
                ,updateDto.getPrice()
                ,updateDto.getStock()
                ,category
        );

        return new ProductResponseDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다."));

        /* 주문된 상품 존재시 삭제 불가 */
        if(!orderRepository.findByProductWhenComplete(productId).isEmpty()){
            throw new DataReferencedException(product.getName() + " 은 주문이 존재합니다.");
        }

        productRepository.delete(product);
    }
}
