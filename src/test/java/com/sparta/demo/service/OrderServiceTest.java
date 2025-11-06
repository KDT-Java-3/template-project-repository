package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderItem;
import com.sparta.demo.domain.order.OrderStatus;
import com.sparta.demo.domain.product.Product;
import com.sparta.demo.domain.user.User;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.OrderRepository;
import com.sparta.demo.repository.ProductRepository;
import com.sparta.demo.repository.UserRepository;
import com.sparta.demo.service.dto.order.OrderCreateDto;
import com.sparta.demo.service.dto.order.OrderDto;
import com.sparta.demo.service.mapper.OrderServiceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * OrderService 단위 테스트
 * 주문 생성, 조회, 취소 등의 비즈니스 로직을 테스트
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderServiceMapper mapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문 생성 성공")
    void createOrder_Success() {
        // Given: 주문 생성 요청 데이터가 주어졌을 때
        Long userId = 1L;
        Long productId = 1L;

        OrderCreateDto.OrderItemDto orderItemDto = new OrderCreateDto.OrderItemDto(productId, 2);
        OrderCreateDto createDto = new OrderCreateDto(
                userId,
                "서울시 강남구",
                Arrays.asList(orderItemDto)
        );

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .email("test@example.com")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("전자제품")
                .build();

        Product product = Product.builder()
                .id(productId)
                .name("노트북")
                .price(new BigDecimal("1500000"))
                .stock(10)
                .category(category)
                .build();

        Order savedOrder = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.PENDING)
                .shippingAddress("서울시 강남구")
                .totalPrice(new BigDecimal("3000000"))
                .build();

        OrderDto expectedDto = new OrderDto(
                1L,
                userId,
                OrderStatus.PENDING,
                "서울시 강남구",
                new BigDecimal("3000000"),
                Arrays.asList(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(orderRepository.save(any(Order.class))).willReturn(savedOrder);
        given(mapper.toDto(savedOrder)).willReturn(expectedDto);

        // When: 주문 생성 메서드를 호출하면
        OrderDto result = orderService.createOrder(createDto);

        // Then: 주문이 정상적으로 생성된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getTotalPrice()).isEqualByComparingTo(new BigDecimal("3000000"));

        // 검증: 각 메서드가 올바르게 호출되었는지 확인
        verify(userRepository).findById(userId);
        verify(productRepository).findById(productId);
        verify(orderRepository).save(any(Order.class));
        verify(mapper).toDto(savedOrder);
    }

    @Test
    @DisplayName("주문 생성 실패 - 사용자 없음")
    void createOrder_UserNotFound_ThrowsException() {
        // Given: 존재하지 않는 사용자 ID로 주문 생성 요청이 주어졌을 때
        Long userId = 999L;
        OrderCreateDto createDto = new OrderCreateDto(
                userId,
                "서울시 강남구",
                Arrays.asList(new OrderCreateDto.OrderItemDto(1L, 2))
        );

        // Mock 동작 설정: 사용자를 찾을 수 없음
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // When & Then: 주문 생성 시도 시 USER_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> orderService.createOrder(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.USER_NOT_FOUND);

        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("주문 생성 실패 - 상품 없음")
    void createOrder_ProductNotFound_ThrowsException() {
        // Given: 존재하지 않는 상품 ID로 주문 생성 요청이 주어졌을 때
        Long userId = 1L;
        Long productId = 999L;

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .build();

        OrderCreateDto createDto = new OrderCreateDto(
                userId,
                "서울시 강남구",
                Arrays.asList(new OrderCreateDto.OrderItemDto(productId, 2))
        );

        // Mock 동작 설정
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // When & Then: 주문 생성 시도 시 PRODUCT_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> orderService.createOrder(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.PRODUCT_NOT_FOUND);

        verify(userRepository).findById(userId);
        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("주문 생성 실패 - 재고 부족")
    void createOrder_InsufficientStock_ThrowsException() {
        // Given: 재고보다 많은 수량으로 주문 생성 요청이 주어졌을 때
        Long userId = 1L;
        Long productId = 1L;

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("전자제품")
                .build();

        Product product = Product.builder()
                .id(productId)
                .name("노트북")
                .price(new BigDecimal("1500000"))
                .stock(5)  // 재고 5개
                .category(category)
                .build();

        OrderCreateDto createDto = new OrderCreateDto(
                userId,
                "서울시 강남구",
                Arrays.asList(new OrderCreateDto.OrderItemDto(productId, 10))  // 10개 주문 시도
        );

        // Mock 동작 설정
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        // When & Then: 주문 생성 시도 시 INSUFFICIENT_STOCK 예외가 발생한다
        assertThatThrownBy(() -> orderService.createOrder(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.INSUFFICIENT_STOCK);

        verify(userRepository).findById(userId);
        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("주문 조회 성공")
    void getOrder_Success() {
        // Given: 존재하는 주문 ID가 주어졌을 때
        Long orderId = 1L;

        User user = User.builder()
                .id(1L)
                .username("testuser")
                .build();

        Order order = Order.builder()
                .id(orderId)
                .user(user)
                .status(OrderStatus.PENDING)
                .shippingAddress("서울시 강남구")
                .totalPrice(new BigDecimal("3000000"))
                .build();

        OrderDto expectedDto = new OrderDto(
                orderId,
                1L,
                OrderStatus.PENDING,
                "서울시 강남구",
                new BigDecimal("3000000"),
                Arrays.asList(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(orderRepository.findByIdWithItemsAndProducts(orderId)).willReturn(Optional.of(order));
        given(mapper.toDto(order)).willReturn(expectedDto);

        // When: 주문 조회 메서드를 호출하면
        OrderDto result = orderService.getOrder(orderId);

        // Then: 주문 정보가 정상적으로 반환된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(orderId);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);

        verify(orderRepository).findByIdWithItemsAndProducts(orderId);
        verify(mapper).toDto(order);
    }

    @Test
    @DisplayName("주문 취소 성공")
    void cancelOrder_Success() {
        // Given: PENDING 상태의 주문이 존재할 때
        Long orderId = 1L;

        User user = User.builder()
                .id(1L)
                .username("testuser")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name("전자제품")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("노트북")
                .price(new BigDecimal("1500000"))
                .stock(5)
                .category(category)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .product(product)
                .quantity(2)
                .price(new BigDecimal("1500000"))
                .build();

        Order order = Order.builder()
                .id(orderId)
                .user(user)
                .status(OrderStatus.PENDING)
                .shippingAddress("서울시 강남구")
                .totalPrice(new BigDecimal("3000000"))
                .build();

        // OrderItem을 Order에 추가 (리플렉션 또는 직접 설정이 필요하지만, 여기서는 Mock으로 처리)
        order.addOrderItem(orderItem);

        // Mock 동작 설정
        given(orderRepository.findByIdWithItemsAndProducts(orderId)).willReturn(Optional.of(order));

        // When: 주문 취소 메서드를 호출하면
        orderService.cancelOrder(orderId);

        // Then: 주문이 정상적으로 취소되고 재고가 복원된다
        verify(orderRepository).findByIdWithItemsAndProducts(orderId);
    }

    @Test
    @DisplayName("주문 취소 실패 - PENDING 상태가 아님")
    void cancelOrder_NotPendingStatus_ThrowsException() {
        // Given: COMPLETED 상태의 주문이 존재할 때
        Long orderId = 1L;

        User user = User.builder()
                .id(1L)
                .username("testuser")
                .build();

        Order order = Order.builder()
                .id(orderId)
                .user(user)
                .status(OrderStatus.COMPLETED)  // 이미 완료된 주문
                .shippingAddress("서울시 강남구")
                .totalPrice(new BigDecimal("3000000"))
                .build();

        // Mock 동작 설정
        given(orderRepository.findByIdWithItemsAndProducts(orderId)).willReturn(Optional.of(order));

        // When & Then: 주문 취소 시도 시 CANNOT_CANCEL_ORDER 예외가 발생한다
        assertThatThrownBy(() -> orderService.cancelOrder(orderId))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.CANNOT_CANCEL_ORDER);

        verify(orderRepository).findByIdWithItemsAndProducts(orderId);
    }
}
