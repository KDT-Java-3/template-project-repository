package com.sparta.demo.order.controller.response;

import com.sparta.demo.category.domain.Category;
import com.sparta.demo.order.domain.Order;
import com.sparta.demo.order.domain.OrderStatus;
import com.sparta.demo.product.domain.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderFindAllResponse(
        Long id,
        OrderStatus orderStatus,
        LocalDateTime orderDateTime,
        ProductDto product
) {

    public static OrderFindAllResponse of(Order order) {
        return new OrderFindAllResponse(
                order.getId(),
                order.getOrderStatus(),
                order.getOrderDateTime(),
                ProductDto.of(order.getProduct())
        );
    }

    public record ProductDto(
            Long id,
            String name,
            String description,
            BigDecimal price,
            CategoryDto category
    ) {

        public record CategoryDto(
                Long id,
                String name,
                String description
        ) {

            public static CategoryDto of(Category category) {
                return new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                );
            }
        }

        public static ProductDto of(Product product) {
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory() != null ? CategoryDto.of(product.getCategory()) : null
            );
        }
    }
}
