package com.example.demo.domain.order.service;

import com.example.demo.domain.Status;
import com.example.demo.domain.category.repositry.CategoryRepository;
import com.example.demo.domain.order.dto.CreateOrderRequestDto;
import com.example.demo.domain.order.dto.OrderResponseDto;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderProduct;
import com.example.demo.domain.order.repository.OrderProductRepository;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 카테고리 ID입니다: " + requestDto.getUserId()));

        Order order = Order.builder()
                .user(user)
                .totalPrice(requestDto.getTotalPrice())
                .shippingAddress(requestDto.getShippingAddress())
                .status(requestDto.getStatus())
                .build();
        Order savedOrder = orderRepository.save(order);
        return OrderResponseDto.from(savedOrder);
    }

    public OrderResponseDto getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        OrderResponseDto response = OrderResponseDto.from(order);
        return response;
    }

    @Transactional
    public void updateOrderStatusComplete(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
        if(order.getStatus() == Status.PENDING){
            order.updateOrderStatus(Status.COMPLETED);
        }else{
            throw new IllegalStateException("order status not set");
        }
    }

    @Transactional
    public void updateOrderStatusCancel(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
        if(order.getStatus() == Status.PENDING){
            order.updateOrderStatus(Status.CANCELED);
        }else{
            throw new IllegalStateException("order status not set");
        }

        // 재고 복원
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(id);

        for(OrderProduct orderProduct : orderProducts){
            orderProduct.getProduct().increaseStock(orderProduct.getQuantity());
        }
    }
}
