package com.sparta.commerce.facade;

import com.sparta.commerce.domain.product.dto.ModifyProductDto;
import com.sparta.commerce.domain.product.dto.ProductDto;
import com.sparta.commerce.domain.product.dto.SaveProductDto;
import com.sparta.commerce.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;

    public ProductDto findProduct(Long id) {
        return productService.findProductById(id);
    }

    public ProductDto saveProduct(SaveProductDto dto) {
        Long productId = productService.saveProduct(dto);
        return productService.findProductById(productId);
    }

    public ProductDto modifyProduct(ModifyProductDto dto) {
        productService.modifyProduct(dto);
        return productService.findProductById(dto.id());
    }
}
