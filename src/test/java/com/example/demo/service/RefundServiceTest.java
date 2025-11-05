package com.example.demo.service;

import com.example.demo.PurchaseStatus;
import com.example.demo.RefundStatus;
import com.example.demo.common.ServiceException;
import com.example.demo.common.ServiceExceptionCode;
import com.example.demo.controller.dto.RefundRequest;
import com.example.demo.controller.dto.RefundResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.Refund;
import com.example.demo.entity.User;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.RefundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RefundServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private RefundRepository refundRepository;

    @InjectMocks
    private RefundService refundService;

    private Purchase purchase;
    private Product product;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("테스트 사용자")
                .email("test@example.com")
                .passwordHash("hash")
                .build();

        Category category = Category.builder()
                .name("액세서리")
                .parent(null)
                .build();

        product = Product.builder()
                .category(category)
                .name("테스트 마우스")
                .description("무선 마우스")
                .price(BigDecimal.valueOf(35000))
                .stock(2)
                .build();

        purchase = Purchase.builder()
                .user(user)
                .product(product)
                .quantity(2)
                .unitPrice(product.getPrice())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(2)))
                .status(PurchaseStatus.COMPLETED)
                .build();
    }

    @Test
    void processRefund_restoresStockAndSavesRefund() {
        // given
        RefundRequest request = RefundRequest.builder()
                .reason("상품에 불량이 있습니다.")
                .build();

        org.mockito.Mockito.when(purchaseRepository.findById(1L))
                .thenReturn(Optional.of(purchase));

        Refund savedRefund = Refund.builder()
                .purchase(purchase)
                .reason(request.getReason())
                .status(RefundStatus.APPROVED)
                .build();

        org.mockito.Mockito.when(refundRepository.save(any(Refund.class)))
                .thenReturn(savedRefund);

        // when
        RefundResponse response = refundService.processRefund(1L, request);

        // then
        assertThat(product.getStock()).isEqualTo(4);
        assertThat(purchase.getStatus()).isEqualTo(PurchaseStatus.REFUNDED);
        assertThat(response.getStatus()).isEqualTo(RefundStatus.APPROVED);
        org.mockito.Mockito.verify(refundRepository).save(any(Refund.class));
    }

    @Test
    void processRefund_invalidStatus_throwsExceptionAndDoesNotRestoreStock() {
        // given
        User user = purchase.getUser();
        purchase = Purchase.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .unitPrice(product.getPrice())
                .totalPrice(product.getPrice())
                .status(PurchaseStatus.PENDING)
                .build();

        org.mockito.Mockito.when(purchaseRepository.findById(1L))
                .thenReturn(Optional.of(purchase));

        RefundRequest request = RefundRequest.builder()
                .reason("마음이 바뀌었습니다.")
                .build();

        // when & then
        assertThatThrownBy(() -> refundService.processRefund(1L, request))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(ServiceExceptionCode.REFUND_NOT_ALLOWED.getMessage());

        assertThat(product.getStock()).isEqualTo(2);
        org.mockito.Mockito.verify(refundRepository, org.mockito.Mockito.never()).save(any(Refund.class));
    }
}
