package com.sparta.proejct1101.domain.order.service;

import com.sparta.proejct1101.domain.order.dto.request.OrderReq;
import com.sparta.proejct1101.domain.order.dto.request.OrderStatusUpdateReq;
import com.sparta.proejct1101.domain.order.dto.response.OrderRes;
import com.sparta.proejct1101.domain.order.entity.Order;
import com.sparta.proejct1101.domain.order.entity.OrderStatus;
import com.sparta.proejct1101.domain.order.repository.OrderRepository;
import com.sparta.proejct1101.domain.product.entity.Product;
import com.sparta.proejct1101.domain.product.repository.ProductRespository;
import com.sparta.proejct1101.domain.user.entity.User;
import com.sparta.proejct1101.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRespository productRespository;

    @Override
    @Transactional
    public OrderRes createOrder(OrderReq req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRespository.findById(req.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.decreaseStock(req.quantity());

        Order order = Order.builder()
                .user(user)
                .product(product)
                .quantity(req.quantity())
                .shippingAddress(req.shippingAddress())
                .status(OrderStatus.PENDING)
                .build();

        orderRepository.save(order);
        return OrderRes.from(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderRes> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserIdWithDetails(userId);
        return orders.stream()
                .map(OrderRes::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderRes getOrder(Long orderId) {
        Order order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderRes.from(order);
    }

    @Override
    @Transactional
    public OrderRes updateOrderStatus(Long orderId, OrderStatusUpdateReq req) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.updateStatus(req.status());

        return OrderRes.from(order);
    }
}
