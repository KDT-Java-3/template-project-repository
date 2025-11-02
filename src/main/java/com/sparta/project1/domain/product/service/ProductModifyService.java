package com.sparta.project1.domain.product.service;

import com.sparta.project1.domain.category.domain.Category;
import com.sparta.project1.domain.category.service.CategoryFindService;
import com.sparta.project1.domain.order.domain.ProductOrder;
import com.sparta.project1.domain.order.domain.ProductOrderEvent;
import com.sparta.project1.domain.order.domain.ProductOrderInfoEvent;
import com.sparta.project1.domain.product.api.dto.ProductRegisterRequest;
import com.sparta.project1.domain.product.domain.Product;
import com.sparta.project1.domain.product.domain.ProductOrderInfo;
import com.sparta.project1.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductModifyService {
    private final ProductRepository productRepository;
    private final CategoryFindService categoryFindService;

    @Transactional
    public void register(ProductRegisterRequest request) {
        Category category = categoryFindService.getById(request.categoryId());

        Product product = Product.register(request.name(), request.price(), request.description(), request.stock(), category);
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long id, ProductRegisterRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("product not found"));

        Category category = categoryFindService.getById(request.categoryId());

        product.updateProduct(
                request.name(),
                request.price(),
                request.description(),
                request.stock(),
                category
        );

        productRepository.save(product);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void minusProductStock(ProductOrderInfoEvent event) {
        List<ProductOrderInfo> productOrderInfos = event.productOrderInfos();
        List<Product> products = productOrderInfos.stream()
                .map(p -> {
                    Product product = p.product();
                    product.minusStock(p.quantity());
                    return product;
                })
                .toList();
        productRepository.saveAll(products);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void plusProductStock(ProductOrderEvent event) {
        List<ProductOrder> productOrders = event.productOrders();
        List<Product> products = productOrders.stream()
                .map(productOrder -> {
                    Product product = productOrder.getProduct();
                    product.plusStock(productOrder.getQuantity());
                    return product;
                }).toList();

        productRepository.saveAll(products);
    }
}
