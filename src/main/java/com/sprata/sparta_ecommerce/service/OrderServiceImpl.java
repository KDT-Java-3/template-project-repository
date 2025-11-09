package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.NotEnoughStockException;
import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.OrderResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.service.dto.OrderServiceSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        /* 상품의 재고수 변경에 대한 동시성 처리 */
        // 락과 함께 Product 조회
        /** 영속성 엔티티 락을 같이 관리해야한다.  */
        Product product = productRepository.getProductLock(orderRequestDto.getProduct_id())
                .orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다."));


        if (product.getStock() < orderRequestDto.getQuantity()) {
            throw new NotEnoughStockException(product.getName(), product.getStock(), orderRequestDto.getQuantity());
        }

        product.updateStock(product.getStock() - orderRequestDto.getQuantity());

        Order order = Order.builder()
                        .userId(orderRequestDto.getUser_id())
                        .product(product)
                        .quantity(orderRequestDto.getQuantity())
                        .shippingAddress(orderRequestDto.getShipping_address())
                        .orderDate(LocalDate.now())
                        .build();
        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByUserId(OrderServiceSearchDto searchDto, PageDto pageDto) {

        List<OrderResponseDto> collect = orderRepository.findByUserWithPaging(searchDto, pageDto).stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("해당 주문을 찾을 수 없습니다."));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("주문 취소는 주문대기 상태에서만 가능합니다.");
        }

        order.updateStatus(status);

        return new OrderResponseDto(order);
    }

    @Override
    @Transactional
    public Long cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("해당 주문을 찾을 수 없습니다."));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("주문 취소는 주문대기 상태에서만 가능합니다.");
        }

        order.updateStatus(OrderStatus.CANCEL_PENDING);

        // 재고수 원복은 확정할때..

        return order.getId();
    }
}
