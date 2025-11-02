package com.sparta.demo.domain.order.service;

import com.sparta.demo.domain.order.dto.request.CreateOrderRequest;
import com.sparta.demo.domain.order.dto.request.UpdateOrderStatusRequest;
import com.sparta.demo.domain.order.dto.response.OrderResponse;
import com.sparta.demo.domain.order.entity.Order;
import com.sparta.demo.domain.order.entity.Product;
import com.sparta.demo.domain.order.entity.User;
import com.sparta.demo.domain.order.repository.OrderRepository;
import com.sparta.demo.domain.order.repository.ProductRepository;
import com.sparta.demo.domain.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // 등록
    public OrderResponse createOrder(CreateOrderRequest request) throws Exception {
        Product product = productRepository.findById(request.getUserId()).orElseThrow(() ->
                new Exception("상품 정보를 찾을 수 없습니다."));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() ->
            new Exception("유저 정보를 찾을 수 없습니다."));
        if(product.getStock() < request.getQuantity()){
            throw new Exception("상품 재고가 부족합니다.");
        }
        Order saved = orderRepository.save(Order.builder()
                        .product(product)
                        .user(user)
                        .shippingAddress(request.getShippingAddress())
                        .quantity(request.getQuantity())
                        .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                        .status(Order.OrderStatus.PENDING)
                .build());
        product.setStock(product.getStock() - request.getQuantity());
        return OrderResponse.buildFromEntity(saved);
    }

    // 사용자 별로 조회
    public List<OrderResponse> getOrderByUserId(Long userId) {
        List<Order> orderList = orderRepository.findAllByUserId(userId);
        return orderList.stream()
                .map(OrderResponse::buildFromEntity)
                .toList();
    }

    // 수정
    public OrderResponse updateOrderStatus(UpdateOrderStatusRequest request) throws Exception {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(()-> new Exception("존재하지 않는 주문입니다."));
        if(request.isChangeable()){
            order.setStatus(request.getNewStatus());
        } else{
            throw new Exception("주문 상태 변경이 불가합니다.");
        }
        orderRepository.save(order);
        return OrderResponse.buildFromEntity(order);
    }
}
