package com.spartaecommerce.product.domain.service;

import com.spartaecommerce.common.exception.BusinessException;
import com.spartaecommerce.common.exception.ErrorCode;
import com.spartaecommerce.order.domain.entity.OrderItem;
import com.spartaecommerce.product.domain.entity.Product;
import com.spartaecommerce.product.domain.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductStockService {

    private final ProductRepository productRepository;

    public ProductStockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deduct(Long productId, Integer quantity) {
        Product product = productRepository.getById(productId);
        product.deductQuantity(quantity);
        productRepository.save(product);
    }

    public void restoreForOrderItems(List<OrderItem> orderItems) {
        List<Long> productIds = orderItems.stream()
            .map(OrderItem::getProductId)
            .toList();

        List<Product> products = productRepository.findAllByIdIn(productIds);

        Map<Long, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductId, Function.identity()));

        orderItems.forEach(orderItem -> {
            Product product = productMap.get(orderItem.getProductId());
            if (product == null) {
                throw new BusinessException(
                    ErrorCode.ENTITY_NOT_FOUND,
                    "Product not found: " + orderItem.getProductId()
                );
            }

            product.restoreQuantity(orderItem.getQuantity());
            productRepository.save(product);
        });
    }
}