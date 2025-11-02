package com.spartaecommerce.product.infrastructure.config;

import com.spartaecommerce.product.domain.repository.ProductRepository;
import com.spartaecommerce.product.domain.service.ProductStockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductDomainConfig {

    @Bean
    public ProductStockService productStockService(ProductRepository productRepository) {
        return new ProductStockService(productRepository);
    }
}
