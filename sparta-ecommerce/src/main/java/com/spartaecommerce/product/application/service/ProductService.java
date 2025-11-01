package com.spartaecommerce.product.application.service;

import com.spartaecommerce.product.application.dto.ProductRegisterCommand;
import com.spartaecommerce.product.domain.Product;
import com.spartaecommerce.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Long register(ProductRegisterCommand registerCommand) {
        // TODO: category 존재여부 확인

        Product product = Product.createNew(
            registerCommand.name(),
            registerCommand.price(),
            registerCommand.stock(),
            registerCommand.categoryId()
        );

        return productRepository.register(product);
    }

    public Product getProduct(Long productId) {
        return productRepository.getProduct(productId);
    }
}
