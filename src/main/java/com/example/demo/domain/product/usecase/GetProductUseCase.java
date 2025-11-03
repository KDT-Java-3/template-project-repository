package com.example.demo.domain.product.usecase;

import com.example.demo.domain.product.dto.ProductDto;
import com.example.demo.domain.product.dto.ProductResponse;
import com.example.demo.domain.product.entity.Product;
import com.example.demo.domain.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductUseCase {
    private final ProductQueryService productQueryService;

    public ProductResponse execute(Long id){
        final ProductDto product = productQueryService.findById(id);
        return ProductResponse.fromDto(product);
    }
}
