package com.wodydtns.commerce.domain.order.service.impl;

import com.wodydtns.commerce.domain.member.entity.Member;
import com.wodydtns.commerce.domain.member.repository.MemberRepository;
import com.wodydtns.commerce.domain.order.dto.CreateOrderDto;
import com.wodydtns.commerce.domain.order.entity.Order;
import com.wodydtns.commerce.domain.order.entity.OrdersProducts;
import com.wodydtns.commerce.domain.order.repository.OrderRepository;
import com.wodydtns.commerce.domain.order.service.OrderService;
import com.wodydtns.commerce.domain.product.entity.Product;
import com.wodydtns.commerce.domain.product.repository.ProductRepository;
import com.wodydtns.commerce.global.enums.OrderStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createOrder(CreateOrderDto createOrderDto) {
        Member member = memberRepository.findById(createOrderDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));

        List<Product> products = createOrderDto.getProductIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: " + productId)))
                .collect(Collectors.toList());

        for (Product product : products) {
            if (!product.hasStock()) {
                throw new RuntimeException("상품 재고가 부족합니다: " + product.getName());
            }
        }

        Order order = new Order(
                member,
                createOrderDto.getQuantity(),
                createOrderDto.getShippingAddress());

        for (Product product : products) {
            OrdersProducts ordersProducts = new OrdersProducts(order, product, OrderStatus.PENDING);
            order.getOrderProducts().add(ordersProducts);
        }

        orderRepository.save(order);

        for (Product product : products) {
            product.decreaseStock();

            productRepository.save(product);
        }
    }

}
