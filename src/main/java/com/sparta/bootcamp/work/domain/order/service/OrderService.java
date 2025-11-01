package com.sparta.bootcamp.work.domain.order.service;

import com.sparta.bootcamp.work.common.enums.OrderStatus;
import com.sparta.bootcamp.work.domain.order.dto.OrderDto;
import com.sparta.bootcamp.work.domain.order.dto.OrderEditRequest;
import com.sparta.bootcamp.work.domain.order.dto.OrderRequest;
import com.sparta.bootcamp.work.domain.order.entity.Order;
import com.sparta.bootcamp.work.domain.order.entity.OrderProduct;
import com.sparta.bootcamp.work.domain.order.repository.OrderProductRepository;
import com.sparta.bootcamp.work.domain.order.repository.OrderRepository;
import com.sparta.bootcamp.work.domain.product.entity.Product;
import com.sparta.bootcamp.work.domain.product.repository.ProductRepository;
import com.sparta.bootcamp.work.domain.user.entity.User;
import com.sparta.bootcamp.work.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .user(userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found") ))
                .status(OrderStatus.PENDING)
                .build();

        Product product = productRepository.findById(orderRequest.getProductId()).orElseThrow(() -> new RuntimeException("Product not found") );

        if(product.getStock() < orderRequest.getQuantity()) {
            product.setStock(product.getStock() - orderRequest.getQuantity());
        }else {
            throw new RuntimeException("Product stock exceeded");
        }

        orderRepository.save(order);
        productRepository.save(product);

        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .price(product.getPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity())) )
                .quantity(orderRequest.getQuantity())
                .shippingAddress(orderRequest.getShippingAddress())
                .build();

        return orderProductRepository.save(orderProduct).getId();
    }

    public List<OrderDto> getOrders(OrderRequest  orderRequest) {

        List<OrderDto> orderDtoList = new ArrayList<>();

        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orderList = user.getOrders();

        for (Order order : orderList) {

            OrderProduct orderProduct = orderProductRepository.findByOrder(order).orElseThrow(() -> new RuntimeException("Order not found"));

            List<Product> products = new ArrayList<>();
            products.add(orderProduct.getProduct());

            orderDtoList.add(OrderDto.fromOrderProducts(order, products));
        }

        return orderDtoList;
    }

    public OrderDto editOrder(OrderEditRequest orderEditRequest) {
        Order order = orderRepository.findById(orderEditRequest.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getStatus() == OrderStatus.PENDING && orderEditRequest.getOrderStatus() != OrderStatus.PENDING) {
            order.setStatus(orderEditRequest.getOrderStatus());
        }
        return OrderDto.fromOrder(orderRepository.save(order)) ;
    }


    public OrderDto cancelOrder(OrderEditRequest orderEditRequest) {
        User user = userRepository.findById(orderEditRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

       Order targetOrder = user.getOrders().stream().filter( i-> i.getId().equals(orderEditRequest.getOrderId()) ).findFirst().get();
        if(targetOrder.getStatus() == OrderStatus.PENDING) {
            targetOrder.setStatus(OrderStatus.CANCELED);
        }
        orderRepository.save(targetOrder);
        return OrderDto.fromOrder(targetOrder);
    }





}
