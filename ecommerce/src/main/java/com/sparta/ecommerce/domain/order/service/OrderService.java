package com.sparta.ecommerce.domain.order.service;

import com.sparta.ecommerce.domain.order.dto.OrderCreateProductDto;
import com.sparta.ecommerce.domain.order.dto.OrderCreateRequest;
import com.sparta.ecommerce.domain.order.dto.OrderResponse;
import com.sparta.ecommerce.domain.order.dto.OrderUpdateStateRequest;
import com.sparta.ecommerce.domain.order.dto.OrderUpsertDto;
import com.sparta.ecommerce.domain.order.entity.Order;
import com.sparta.ecommerce.domain.order.entity.OrderProduct;
import com.sparta.ecommerce.domain.order.entity.OrderStatus;
import com.sparta.ecommerce.domain.order.repository.OrderRepository;
import com.sparta.ecommerce.domain.product.entity.Product;
import com.sparta.ecommerce.domain.product.repository.ProductRepository;
import com.sparta.ecommerce.domain.user.entity.User;
import com.sparta.ecommerce.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Generated;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderUpsertDto createOrder(OrderCreateRequest dto) {
        User user = (User)this.userRepository.getReferenceById(dto.getUserId());
        Order order = Order.builder().shippingAddress(dto.getShippingAddress()).user(user).build();
        List<Long> productIds = dto.getProducts().stream().map(OrderCreateProductDto::getProductId).toList();
        List<Product> products = this.productRepository.findAllById(productIds);
        Map<Long, Product> productMap = (Map)products.stream().collect(Collectors.toMap(Product::getId, (p) -> p));
        dto.getProducts().forEach((p) -> {
            Product product = (Product)productMap.get(p.getProductId());
            product.decrease(p.getQuantity());
            OrderProduct orderProduct = OrderProduct.builder().order(order).product(product).quantity(p.getQuantity()).price(product.getPrice()).build();
            order.addProduct(orderProduct);
        });
        this.orderRepository.save(order);
        return OrderUpsertDto.fromEntity(order);
    }

    @Transactional(
            readOnly = true
    )
    public List<OrderResponse> readOrderByUser(Long id) {
        List<Order> orders = this.orderRepository.findByUserId(id);
        List<OrderResponse> res = orders.stream().map(OrderResponse::fromEntity).toList();
        return res;
    }

    public OrderUpsertDto updateState(OrderUpdateStateRequest dto) {
        Order order = (Order)this.orderRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        if (dto.getOrderStatus() == OrderStatus.completed) {
            order.complete();
        }

        if (dto.getOrderStatus() == OrderStatus.canceled) {
            order.cancel();
        }

        return OrderUpsertDto.fromEntity(order);
    }

    @Generated
    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository, final ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
}
