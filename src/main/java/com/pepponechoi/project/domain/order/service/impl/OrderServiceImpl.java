package com.pepponechoi.project.domain.order.service.impl;

import com.pepponechoi.project.common.enums.OrderStatus;
import com.pepponechoi.project.domain.order.dto.request.OrderChangeStatusRequest;
import com.pepponechoi.project.domain.order.dto.request.OrderCreateRequest;
import com.pepponechoi.project.domain.order.dto.request.OrderDeleteRequest;
import com.pepponechoi.project.domain.order.dto.response.OrderResponse;
import com.pepponechoi.project.domain.order.entity.Order;
import com.pepponechoi.project.domain.order.repository.OrderRepository;
import com.pepponechoi.project.domain.order.service.OrderService;
import com.pepponechoi.project.domain.product.dto.response.ProductResponse;
import com.pepponechoi.project.domain.product.entity.Product;
import com.pepponechoi.project.domain.product.repository.ProductRepository;
import com.pepponechoi.project.domain.user.entity.User;
import com.pepponechoi.project.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse create(OrderCreateRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return null;
        }

        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return null;
        }

        Order order = new Order(user, product, request.getQuantity(), request.getShippingAddress());
        orderRepository.save(order);

        return new OrderResponse(
            order.getId(),
            order.getStatus().getDescription(),
            order.getCreatedAt(),
            new ProductResponse(
                order.getProduct().getId(),
                order.getProduct().getName(),
                order.getProduct().getDescription(),
                order.getProduct().getPrice(),
                order.getProduct().getStock()
            )
        );
    }

    @Override
    public List<OrderResponse> findAllByUser(Long userId) {

        List<Order> orders = orderRepository.findAllByUser_IdFetch(userId);
        if (orders.isEmpty()) {
            return null;
        }

        return orders.stream()
            .map(order -> new OrderResponse(
                order.getId(),
                order.getStatus().getDescription(),
                order.getCreatedAt(),
                new ProductResponse(
                    order.getProduct().getId(),
                    order.getProduct().getName(),
                    order.getProduct().getDescription(),
                    order.getProduct().getPrice(),
                    order.getProduct().getStock()
                )
            ))
            .toList();
    }

    @Override
    @Transactional
    public Boolean changeStatus(OrderChangeStatusRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return false;
        }

        Order order = orderRepository.findById(request.getOrderId()).orElse(null);
        if (order == null) {
            return false;
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            return false;
        }

        if (!order.getUser().getId().equals(user.getId())) {
            return false;
        }

        order.changeStatus(request.getStatus());
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(OrderDeleteRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return false;
        }

        Order order = orderRepository.findById(request.getId()).orElse(null);
        if (order == null) {
            return false;
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            return false;
        }

        if (!order.getUser().getId().equals(user.getId())) {
            return false;
        }

        order.getProduct().addStock(order.getQuantity());
        order.getProduct().removeOrder(order);
        order.getUser().removeOrder(order);
        orderRepository.delete(order);

        return true;
    }
}