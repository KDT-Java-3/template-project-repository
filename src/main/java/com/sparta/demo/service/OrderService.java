package com.sparta.demo.service;

import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderItem;
import com.sparta.demo.domain.order.OrderStatus;
import com.sparta.demo.domain.product.Product;
import com.sparta.demo.domain.user.User;
import com.sparta.demo.dto.order.OrderCreateDto;
import com.sparta.demo.dto.order.OrderDto;
import com.sparta.demo.repository.OrderRepository;
import com.sparta.demo.repository.ProductRepository;
import com.sparta.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderDto createOrder(OrderCreateDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다. ID: " + dto.getUserId()));

        Order order = Order.create(user, dto.getShippingAddress());

        dto.getOrderItems().forEach(itemDto -> {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + itemDto.getProductId()));

            if (product.getStock() < itemDto.getQuantity()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "재고가 부족합니다. 상품 ID: " + product.getId() +
                        ", 요청 수량: " + itemDto.getQuantity() +
                        ", 현재 재고: " + product.getStock()
                );
            }

            product.decreaseStock(itemDto.getQuantity());

            OrderItem orderItem = OrderItem.create(product, itemDto.getQuantity());
            order.addOrderItem(orderItem);
        });

        order.calculateTotalPrice();

        Order savedOrder = orderRepository.save(order);
        return OrderDto.from(savedOrder);
    }

    public OrderDto getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + id));
        return OrderDto.from(order);
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByUserIdAndStatus(Long userId, OrderStatus status) {
        return orderRepository.findByUser_IdAndStatus(userId, status).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + id));
        order.updateStatus(status);
        return OrderDto.from(order);
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다. ID: " + id));

        // PENDING 상태의 주문만 취소 가능
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "PENDING 상태의 주문만 취소할 수 있습니다. 현재 상태: " + order.getStatus());
        }

        try {
            order.cancel();
            order.getOrderItems().forEach(orderItem -> {
                Product product = orderItem.getProduct();
                product.increaseStock(orderItem.getQuantity());
            });
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
