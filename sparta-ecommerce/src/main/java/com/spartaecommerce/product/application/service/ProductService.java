package com.spartaecommerce.product.application.service;

import com.spartaecommerce.product.domain.command.ProductRegisterCommand;
import com.spartaecommerce.product.domain.command.ProductUpdateCommand;
import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.query.ProductSearchQuery;
import com.spartaecommerce.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long register(ProductRegisterCommand registerCommand) {
        // TODO: category 존재여부 확인
        // TODO: name 중복 여부 검증
        Product product = Product.createNew(registerCommand);
        return productRepository.save(product);
    }

    @Transactional
    public void update(Long productId, ProductUpdateCommand updateCommand) {
        // TODO: name 중보 여부 검증
        Product product = productRepository.getById(productId);
        product.update(updateCommand);
        productRepository.save(product);
    }

    public Product getProduct(Long productId) {
        return productRepository.getById(productId);
    }

    // TODO: Pagination 변경 예정
    public List<Product> search(ProductSearchQuery searchQuery) {
        return productRepository.search(searchQuery);
    }
}
