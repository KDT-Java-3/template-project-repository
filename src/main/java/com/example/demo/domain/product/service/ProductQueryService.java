package com.example.demo.domain.product.service;

import com.example.demo.common.exception.CommonException;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.domain.product.dto.ProductDto;
import com.example.demo.domain.product.entity.Product;
import com.example.demo.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND, Map.of("id", id)));
        return ProductDto.fromEntity(product);
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDto::fromEntity).collect(Collectors.toList());
    }
}
