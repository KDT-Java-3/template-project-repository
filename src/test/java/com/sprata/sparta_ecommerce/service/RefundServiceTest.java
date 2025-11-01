package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.dto.OrderRequestDto;
import com.sprata.sparta_ecommerce.dto.RefundRequestDto;
import com.sprata.sparta_ecommerce.dto.RefundResponseDto;
import com.sprata.sparta_ecommerce.entity.*;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.repository.RefundRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RefundServiceTest {

    @Autowired
    private RefundService refundService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        // 카테고리
        Category category = Category.builder()
                .name("테스트카테고리")
                .description("상세설명")
                .build();
        categoryRepository.save(category);

        // 상품 준비
        product = Product.builder()
                .name("테스트상품")
                .description("테스트상품내용")
                .price(10000L)
                .stock(10)
                .category(category)
                .build();
        productRepository.save(product);

        // 주문 생성
        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setUser_id(1L);
        orderDto.setProduct_id(product.getId());
        orderDto.setQuantity(1);
        orderDto.setShipping_address("서울시 강남구");
        orderService.createOrder(orderDto);

        this.order = orderRepository.findAll().get(0);
    }


    @Test
    @DisplayName("✅ 환불 요청 성공")
    void requestRefund_Success() {
        RefundRequestDto dto = new RefundRequestDto();
        dto.setUser_id(1L);
        dto.setOrder_id(order.getId());
        dto.setReason("단순 변심");

        RefundResponseDto response = refundService.requestRefund(dto);

        assertThat(response).isNotNull();
        assertThat(response.getOrder_id()).isEqualTo(order.getId());
        assertThat(response.getReason()).isEqualTo("단순 변심");
    }

    @Test
    @DisplayName("❌ 환불 요청 실패 - 존재하지 않는 주문")
    void requestRefund_Fail_OrderNotFound() {
        RefundRequestDto dto = new RefundRequestDto();
        dto.setUser_id(1L);
        dto.setOrder_id(9999L);
        dto.setReason("테스트 실패 케이스");

        assertThrows(DataNotFoundException.class, () -> refundService.requestRefund(dto));
    }

    @Test
    @DisplayName("✅ 환불 처리 성공 - 취소된 주문의 재고 복구")
    void processRefund_Success() {
        // 주문 취소 상태로 변경
        order.updateStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        // 환불 요청 저장
        Refund refund = refundRepository.save(
                Refund.builder()
                        .userId(order.getUserId())
                        .order(order)
                        .reason("테스트 환불")
                        .build()
        );

        int originalStock = product.getStock();

        RefundResponseDto response = refundService.processRefund(refund.getId(), RefundStatus.APPROVED);

        assertThat(response.getStatus()).isEqualTo(RefundStatus.APPROVED);
        assertThat(product.getStock()).isEqualTo(originalStock + order.getQuantity());
    }

    @Test
    @DisplayName("❌ 환불 처리 실패 - 주문이 취소되지 않음")
    void processRefund_Fail_OrderNotCanceled() {
        Refund refund = refundRepository.save(
                Refund.builder()
                        .userId(order.getUserId())
                        .order(order)
                        .reason("테스트 실패")
                        .build()
        );

        assertThrows(DataNotFoundException.class,
                () -> refundService.processRefund(refund.getId(), RefundStatus.APPROVED));
    }

    @Test
    @DisplayName("✅ 특정 유저의 환불 목록 조회 성공")
    void getRefundsByUserId_Success() {
        // 준비: 주문 취소 + 환불 요청 생성
        order.updateStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        Refund refund = Refund.builder()
                .userId(order.getUserId())
                .order(order)
                .reason("테스트 조회")
                .build();
        refundRepository.save(refund);

        List<RefundResponseDto> refunds = refundService.getRefundsByUserId(order.getUserId());

        assertThat(refunds).isNotEmpty();
        assertThat(refunds.get(0).getUser_id()).isEqualTo(order.getUserId());
    }

    @Test
    @DisplayName("✅ 특정 유저의 환불 목록 조회 - 환불 없음")
    void getRefundsByUserId_Empty() {
        List<RefundResponseDto> refunds = refundService.getRefundsByUserId(9999L);
        assertThat(refunds).isEmpty();
    }
}