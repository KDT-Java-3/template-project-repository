package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.NotEnoughStockException;
import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.OrderResponseDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;

    private Product product;
    private Category category;


    @BeforeEach
    void setUp() {
        category = categoryRepository.save(new Category("가전", "전자제품",null));
        product = productRepository.save(new Product("삼성 노트북", "고성능 노트북", 1500L, 10, category));

        em.flush();
        em.clear();
    }


    @Test
    @DisplayName("주문 생성 성공")
    void createOrder_success() {
        OrderRequestDto dto = new OrderRequestDto(1L,product.getId(),  1, "서울시 송파구");
        OrderResponseDto response = orderService.createOrder(dto);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(productRepository.findById(product.getId()).get().getStock()).isEqualTo(9);
    }

    @Test
    @DisplayName("주문 조회 성공")
    void getOrdersByUserId_success() {
        // given
        OrderRequestDto dto = new OrderRequestDto(1L, product.getId(),  1, "서울시 송파구");
        orderService.createOrder(dto);

        // when
        List<OrderResponseDto> result = orderService.getOrdersByUserId(1L);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProduct_name()).isEqualTo("삼성 노트북");
    }

    @Test
    @DisplayName("주문 상태 변경 성공")
    void updateOrderStatus_success() {
        OrderRequestDto dto = new OrderRequestDto(1L, product.getId(),  1, "서울시 송파구");
        OrderResponseDto created = orderService.createOrder(dto);

        OrderResponseDto updated = orderService.updateOrderStatus(created.getId(), OrderStatus.COMPLETED);

        assertThat(updated.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    @DisplayName("주문 취소 성공")
    void cancelOrder_success() {
        OrderRequestDto dto = new OrderRequestDto(1L, product.getId(),  1, "서울시 송파구");
        OrderResponseDto created = orderService.createOrder(dto);

        Long canceledId = orderService.cancelOrder(created.getId());

        Order order = orderRepository.findById(created.getId()).get();
        assertThat(canceledId).isEqualTo(created.getId());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL_PENDING);
    }

    // ---------------------------- [실패 시나리오] ----------------------------

    @Test
    @DisplayName("존재하지 않는 상품 주문 시 실패")
    void createOrder_fail_invalidProduct() {
        OrderRequestDto dto = new OrderRequestDto(999L, 2L, 1, "서울시 송파구");

        assertThatThrownBy(() -> orderService.createOrder(dto))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    @DisplayName("재고 부족으로 주문 실패")
    void createOrder_fail_notEnoughStock() {
        OrderRequestDto dto = new OrderRequestDto(1L,product.getId(), 999, "서울시 송파구");

        assertThatThrownBy(() -> orderService.createOrder(dto))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    @DisplayName("주문상태 변경 실패 - 이미 완료된 주문")
    void updateOrderStatus_fail_notPending() {
        OrderRequestDto dto = new OrderRequestDto(1L,product.getId(),  1, "서울시 송파구");
        OrderResponseDto created = orderService.createOrder(dto);
        orderService.updateOrderStatus(created.getId(), OrderStatus.COMPLETED);

        assertThatThrownBy(() -> orderService.updateOrderStatus(created.getId(), OrderStatus.CANCELED))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문 취소 실패 - 이미 완료된 주문")
    void cancelOrder_fail_notPending() {
        OrderRequestDto dto = new OrderRequestDto(1L,product.getId(),  1, "서울시 송파구");
        OrderResponseDto created = orderService.createOrder(dto);
        orderService.updateOrderStatus(created.getId(), OrderStatus.COMPLETED);

        assertThatThrownBy(() -> orderService.cancelOrder(created.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}