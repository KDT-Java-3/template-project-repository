package com.sparta.week1.order;

import com.sparta.week1.dto.order.CreateOrderDto;
import com.sparta.week1.entity.Order;
import com.sparta.week1.entity.Product;
import com.sparta.week1.entity.User;
import com.sparta.week1.product.ProductRepository;
import com.sparta.week1.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository repository,  UserRepository userRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder (CreateOrderDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Order order = Order.builder().product(product).user(user).quantity(dto.getQuantity()).build();

        return repository.save(order);
    }

    public List<Order> getOrderById (Long id) {
        List<Order> orders = repository.findAllByUserId(id);
        return orders;
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 정보 입니다."));

        if (status != null){
            order.updateStatus(status);
        }

        return order;
    }

    @Transactional
    public Order deleteOrder(long id) {
        Order order = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 정보 입니다."));

        repository.delete(order);
        return order;
    }
}
