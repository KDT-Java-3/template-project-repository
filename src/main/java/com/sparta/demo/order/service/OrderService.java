package com.sparta.demo.order.service;

import com.sparta.demo.order.domain.Order;
import com.sparta.demo.order.domain.OrderRepository;
import com.sparta.demo.order.service.command.OrderSaveCommand;
import com.sparta.demo.order.service.command.OrderStatusChangeCommand;
import com.sparta.demo.product.domain.Product;
import com.sparta.demo.product.domain.ProductRepository;
import com.sparta.demo.user.domain.User;
import com.sparta.demo.user.domain.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Long save(OrderSaveCommand command) {
        User user = userRepository.getById(command.userId());
        Product product = productRepository.getById(command.productId());

        Order order = Order.create(user, product, command.quantity(), command.shippingAddress());
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional(readOnly = true)
    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void changeStatus(OrderStatusChangeCommand command) {
        Order order = orderRepository.getById(command.orderId());
        order.changeStatus(command.newStatus());
    }

    public void cancel(Long orderId) {
        Order order = orderRepository.getById(orderId);
        order.cancel();
    }
}
