package com.sparta.commerce.domain.order.service;

import com.sparta.commerce.domain.order.dto.CreateOrderDto;
import com.sparta.commerce.domain.order.dto.OrderDto;
import com.sparta.commerce.domain.order.dto.OrderItemDto;
import com.sparta.commerce.domain.order.repository.OrderProductRepository;
import com.sparta.commerce.domain.order.repository.OrderRepository;
import com.sparta.commerce.entity.Order;
import com.sparta.commerce.entity.OrderProduct;
import com.sparta.commerce.entity.Product;
import com.sparta.commerce.entity.User;
import com.sparta.commerce.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public OrderDto createOrder(
            CreateOrderDto dto,
            User user,
            Map<Long, Product> productMap
    ) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemDto item : dto.orderItems()) {
            Product product = productMap.get(item.productId());

            if (product.getStock() < item.quantity()) {
                throw new RuntimeException("상품 '" + product.getName() + "'의 재고가 부족합니다.");
            }

            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            totalPrice = totalPrice.add(itemPrice);
        }

        Order order = Order.builder()
                .user(user)
                .shippingAddress(dto.shippingAddress())
                .totalPrice(totalPrice)
                .status(OrderStatus.PENDING)
                .build();

        orderRepository.save(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderItemDto item : dto.orderItems()) {
            Product product = productMap.get(item.productId());

            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(item.quantity())
                    .price(product.getPrice())
                    .build();

            orderProducts.add(orderProduct);
        }

        orderProductRepository.saveAll(orderProducts);

        return OrderDto.of(order, orderProducts);
    }

}
