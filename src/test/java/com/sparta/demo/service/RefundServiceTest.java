package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.domain.order.Order;
import com.sparta.demo.domain.order.OrderItem;
import com.sparta.demo.domain.order.OrderStatus;
import com.sparta.demo.domain.product.Product;
import com.sparta.demo.domain.refund.Refund;
import com.sparta.demo.domain.refund.RefundStatus;
import com.sparta.demo.domain.user.User;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.OrderRepository;
import com.sparta.demo.repository.RefundRepository;
import com.sparta.demo.repository.UserRepository;
import com.sparta.demo.service.dto.refund.RefundCreateDto;
import com.sparta.demo.service.dto.refund.RefundDto;
import com.sparta.demo.service.mapper.RefundServiceMapper;
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
 * RefundService 단위 테스트
 * 환불 생성, 승인, 거절 등의 비즈니스 로직을 테스트
 */
@ExtendWith(MockitoExtension.class)
class RefundServiceTest {

    @Mock
    private RefundRepository refundRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefundServiceMapper mapper;

    @InjectMocks
    private RefundService refundService;

    @Test
    @DisplayName("환불 요청 생성 성공")
    void createRefund_Success() {
        // Given: COMPLETED 상태의 주문에 대한 환불 요청이 주어졌을 때
        Long userId = 1L;
        Long orderId = 1L;

        RefundCreateDto createDto = new RefundCreateDto(
                orderId,
                userId,
                "상품 불량"
        );

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .email("test@example.com")
                .build();

        Order order = Order.builder()
                .id(orderId)
                .user(user)
                .status(OrderStatus.COMPLETED)  // 완료된 주문만 환불 가능
                .shippingAddress("서울시 강남구")
                .totalPrice(new BigDecimal("3000000"))
                .build();

        Refund savedRefund = Refund.builder()
                .id(1L)
                .order(order)
                .user(user)
                .reason("상품 불량")
                .status(RefundStatus.PENDING)
                .build();

        RefundDto expectedDto = new RefundDto(
                1L,
                orderId,
                userId,
                "상품 불량",
                RefundStatus.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
        given(refundRepository.save(any(Refund.class))).willReturn(savedRefund);
        given(mapper.toDto(savedRefund)).willReturn(expectedDto);

        // When: 환불 요청 생성 메서드를 호출하면
        RefundDto result = refundService.createRefund(createDto);

        // Then: 환불 요청이 정상적으로 생성된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getOrderId()).isEqualTo(orderId);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getStatus()).isEqualTo(RefundStatus.PENDING);
        assertThat(result.getReason()).isEqualTo("상품 불량");

        // 검증: 각 메서드가 올바르게 호출되었는지 확인
        verify(userRepository).findById(userId);
        verify(orderRepository).findById(orderId);
        verify(refundRepository).save(any(Refund.class));
        verify(mapper).toDto(savedRefund);
    }

    @Test
    @DisplayName("환불 요청 생성 실패 - 사용자 없음")
    void createRefund_UserNotFound_ThrowsException() {
        // Given: 존재하지 않는 사용자 ID로 환불 요청이 주어졌을 때
        Long userId = 999L;
        RefundCreateDto createDto = new RefundCreateDto(
                1L,
                userId,
                "상품 불량"
        );

        // Mock 동작 설정: 사용자를 찾을 수 없음
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // When & Then: 환불 요청 생성 시도 시 USER_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> refundService.createRefund(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.USER_NOT_FOUND);

        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("환불 요청 생성 실패 - 주문 없음")
    void createRefund_OrderNotFound_ThrowsException() {
        // Given: 존재하지 않는 주문 ID로 환불 요청이 주어졌을 때
        Long userId = 1L;
        Long orderId = 999L;

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .build();

        RefundCreateDto createDto = new RefundCreateDto(
                orderId,
                userId,
                "상품 불량"
        );

        // Mock 동작 설정
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(orderRepository.findById(orderId)).willReturn(Optional.empty());

        // When & Then: 환불 요청 생성 시도 시 ORDER_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> refundService.createRefund(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.ORDER_NOT_FOUND);

        verify(userRepository).findById(userId);
        verify(orderRepository).findById(orderId);
    }

    @Test
    @DisplayName("환불 요청 생성 실패 - COMPLETED 상태가 아닌 주문")
    void createRefund_InvalidOrderStatus_ThrowsException() {
        // Given: PENDING 상태의 주문에 대한 환불 요청이 주어졌을 때
        Long userId = 1L;
        Long orderId = 1L;

        User user = User.builder()
                .id(userId)
                .username("testuser")
                .build();

        Order order = Order.builder()
                .id(orderId)
                .user(user)
                .status(OrderStatus.PENDING)  // 완료되지 않은 주문
                .shippingAddress("서울시 강남구")
                .totalPrice(new BigDecimal("3000000"))
                .build();

        RefundCreateDto createDto = new RefundCreateDto(
                orderId,
                userId,
                "상품 불량"
        );

        // Mock 동작 설정
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));

        // When & Then: 환불 요청 생성 시도 시 INVALID_ORDER_STATUS 예외가 발생한다
        assertThatThrownBy(() -> refundService.createRefund(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.INVALID_ORDER_STATUS);

        verify(userRepository).findById(userId);
        verify(orderRepository).findById(orderId);
    }

    @Test
    @DisplayName("환불 조회 성공")
    void getRefund_Success() {
        // Given: 존재하는 환불 ID가 주어졌을 때
        Long refundId = 1L;

        User user = User.builder()
                .id(1L)
                .username("testuser")
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.COMPLETED)
                .totalPrice(new BigDecimal("3000000"))
                .build();

        Refund refund = Refund.builder()
                .id(refundId)
                .order(order)
                .user(user)
                .reason("상품 불량")
                .status(RefundStatus.PENDING)
                .build();

        RefundDto expectedDto = new RefundDto(
                refundId,
                1L,
                1L,
                "상품 불량",
                RefundStatus.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(refundRepository.findById(refundId)).willReturn(Optional.of(refund));
        given(mapper.toDto(refund)).willReturn(expectedDto);

        // When: 환불 조회 메서드를 호출하면
        RefundDto result = refundService.getRefund(refundId);

        // Then: 환불 정보가 정상적으로 반환된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(refundId);
        assertThat(result.getStatus()).isEqualTo(RefundStatus.PENDING);

        verify(refundRepository).findById(refundId);
        verify(mapper).toDto(refund);
    }

    @Test
    @DisplayName("환불 승인 성공")
    void approveRefund_Success() {
        // Given: REQUESTED 상태의 환불이 존재할 때
        Long refundId = 1L;

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
                .id(1L)
                .user(user)
                .status(OrderStatus.COMPLETED)
                .totalPrice(new BigDecimal("3000000"))
                .build();

        order.addOrderItem(orderItem);

        Refund refund = Refund.builder()
                .id(refundId)
                .order(order)
                .user(user)
                .reason("상품 불량")
                .status(RefundStatus.PENDING)
                .build();

        RefundDto expectedDto = new RefundDto(
                refundId,
                1L,
                1L,
                "상품 불량",
                RefundStatus.APPROVED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(refundRepository.findById(refundId)).willReturn(Optional.of(refund));
        given(mapper.toDto(refund)).willReturn(expectedDto);

        // When: 환불 승인 메서드를 호출하면
        RefundDto result = refundService.approveRefund(refundId);

        // Then: 환불이 승인되고 재고가 복원된다
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(RefundStatus.APPROVED);

        verify(refundRepository).findById(refundId);
        verify(mapper).toDto(refund);
    }

    @Test
    @DisplayName("환불 거절 성공")
    void rejectRefund_Success() {
        // Given: REQUESTED 상태의 환불이 존재할 때
        Long refundId = 1L;

        User user = User.builder()
                .id(1L)
                .username("testuser")
                .build();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .status(OrderStatus.COMPLETED)
                .totalPrice(new BigDecimal("3000000"))
                .build();

        Refund refund = Refund.builder()
                .id(refundId)
                .order(order)
                .user(user)
                .reason("단순 변심")
                .status(RefundStatus.PENDING)
                .build();

        RefundDto expectedDto = new RefundDto(
                refundId,
                1L,
                1L,
                "단순 변심",
                RefundStatus.REJECTED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(refundRepository.findById(refundId)).willReturn(Optional.of(refund));
        given(mapper.toDto(refund)).willReturn(expectedDto);

        // When: 환불 거절 메서드를 호출하면
        RefundDto result = refundService.rejectRefund(refundId);

        // Then: 환불이 거절된다
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(RefundStatus.REJECTED);

        verify(refundRepository).findById(refundId);
        verify(mapper).toDto(refund);
    }

    @Test
    @DisplayName("환불 조회 실패 - 환불 없음")
    void getRefund_RefundNotFound_ThrowsException() {
        // Given: 존재하지 않는 환불 ID가 주어졌을 때
        Long refundId = 999L;

        // Mock 동작 설정: 환불을 찾을 수 없음
        given(refundRepository.findById(refundId)).willReturn(Optional.empty());

        // When & Then: 환불 조회 시도 시 REFUND_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> refundService.getRefund(refundId))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.REFUND_NOT_FOUND);

        verify(refundRepository).findById(refundId);
    }
}
