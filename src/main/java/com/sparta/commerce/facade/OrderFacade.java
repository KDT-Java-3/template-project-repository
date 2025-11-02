package com.sparta.commerce.facade;

import com.sparta.commerce.domain.order.dto.CreateOrderDto;
import com.sparta.commerce.domain.order.dto.OrderDto;
import com.sparta.commerce.domain.order.dto.OrderItemDto;
import com.sparta.commerce.domain.order.service.OrderService;
import com.sparta.commerce.domain.product.service.ProductService;
import com.sparta.commerce.domain.user.service.UserService;
import com.sparta.commerce.entity.Product;
import com.sparta.commerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    private final ProductService productService;

    private final UserService userService;

    public OrderDto createOrder(CreateOrderDto dto) {
        User user = userService.findUserById(dto.userId());

        List<Long> productIds = dto.orderItems().stream()
                .map(OrderItemDto::productId)
                .toList();

        Map<Long, Product> productMap = productIds.stream()
                .map(productService::findProductEntityById)
                .collect(Collectors.toMap(Product::getId, product -> product));

        return orderService.createOrder(dto, user, productMap);
    }
}
