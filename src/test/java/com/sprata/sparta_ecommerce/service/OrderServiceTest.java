package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.NotEnoughStockException;
import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.OrderResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.service.dto.OrderServiceSearchDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
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
        em.flush();
        em.clear();

        // when
        OrderServiceSearchDto searchDto = new OrderServiceSearchDto(1L, "", null, null);
        PageDto pageDto = new PageDto();
        List<OrderResponseDto> result = orderService.getOrdersByUserId(searchDto, pageDto);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProduct_name()).isEqualTo("삼성 노트북");
    }

    @Test
    @DisplayName("주문 검색 (상태, 날짜 범위) 성공")
    void getOrdersByUserId_withSearchCriteria_success() {
        // Given: 여러 주문 생성
       LocalDate today =LocalDate.now();
       LocalDate yesterday = today.minusDays(1);
       LocalDate twoDaysAgo = today.minusDays(2);

        Product product2 = productRepository.save(new Product("LG 그램", "가벼운 노트북", 1200L, 5, category));
        Product product3 = productRepository.save(new Product("맥북 에어", "애플 노트북", 1800L, 3, category));

        // 1. PENDING 상태, 오늘 날짜
        Order order1 = orderRepository.save(Order.builder().userId(1L).product(product).quantity(1).shippingAddress("서울시 송파구").orderDate(today).build());
        // 2. COMPLETED 상태, 어제 날짜
        Order order2 = orderRepository.save(Order.builder().userId(1L).product(product2).quantity(1).shippingAddress("경기도 성남시").orderDate(yesterday).build());
        order2.updateStatus(OrderStatus.COMPLETED);
        // 3. CANCEL_PENDING 상태, 2일 전 날짜
        Order order3 = orderRepository.save(Order.builder().userId(1L).product(product3).quantity(1).shippingAddress("부산시 해운대구").orderDate(twoDaysAgo).build());
        order3.updateStatus(OrderStatus.CANCEL_PENDING);

        em.flush();
        em.clear();

        // When 1: PENDING 상태로 검색
        OrderServiceSearchDto searchDto1 = new OrderServiceSearchDto(1L, OrderStatus.PENDING.name(), null, null);
        PageDto pageDto1 = new PageDto();
        List<OrderResponseDto> result1 = orderService.getOrdersByUserId(searchDto1, pageDto1);

        // Then 1: PENDING 상태의 주문 1개 확인
        assertThat(result1).hasSize(1);
        assertThat(result1.get(0).getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result1.get(0).getProduct_name()).isEqualTo("삼성 노트북");

        // When 2: COMPLETED 상태로 검색
        OrderServiceSearchDto searchDto2 = new OrderServiceSearchDto(1L, OrderStatus.COMPLETED.name(), null, null);
        PageDto pageDto2 = new PageDto();
        List<OrderResponseDto> result2 = orderService.getOrdersByUserId(searchDto2, pageDto2);

        // Then 2: COMPLETED 상태의 주문 1개 확인
        assertThat(result2).hasSize(1);
        assertThat(result2.get(0).getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(result2.get(0).getProduct_name()).isEqualTo("LG 그램");

        // When 3: 날짜 범위로 검색 (어제부터 오늘까지)
        OrderServiceSearchDto searchDto3 = new OrderServiceSearchDto(1L, null, yesterday, today);
        PageDto pageDto3 = new PageDto();
        List<OrderResponseDto> result3 = orderService.getOrdersByUserId(searchDto3, pageDto3);

        // Then 3: 어제와 오늘 생성된 주문 2개 확인 (PENDING, COMPLETED)
        assertThat(result3).hasSize(2);
        assertThat(result3).extracting(OrderResponseDto::getStatus)
                .containsExactlyInAnyOrder(OrderStatus.PENDING, OrderStatus.COMPLETED);

        // When 4: 상태와 날짜 범위 조합 검색 (PENDING 상태, 어제부터 오늘까지)
        OrderServiceSearchDto searchDto4 = new OrderServiceSearchDto(1L, OrderStatus.PENDING.name(), yesterday, today);
        PageDto pageDto4 = new PageDto();
        List<OrderResponseDto> result4 = orderService.getOrdersByUserId(searchDto4, pageDto4);

        // Then 4: PENDING 상태이면서 어제부터 오늘까지 생성된 주문 1개 확인
        assertThat(result4).hasSize(1);
        assertThat(result4.get(0).getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result4.get(0).getProduct_name()).isEqualTo("삼성 노트북");
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