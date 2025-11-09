package com.example.demo.service;

import com.example.demo.PurchaseStatus;
import com.example.demo.common.ServiceException;
import com.example.demo.common.ServiceExceptionCode;
import com.example.demo.controller.dto.PurchaseRequest;
import com.example.demo.controller.dto.PurchaseResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Purchase;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("테스트 사용자")
                .email("test@example.com")
                .passwordHash("hash")
                .build();

        Category category = Category.builder()
                .name("노트북")
                .parent(null)
                .build();

        product = Product.builder()
                .category(category)
                .name("테스트 노트북")
                .description("사양: i7 / 16GB")
                .price(BigDecimal.valueOf(1500000))
                .stock(10)
                .build();
    }

    @Test
    void placePurchase_successfullyCreatesPurchaseAndDecreasesStock() {
        // given
        PurchaseRequest request = PurchaseRequest.builder()
                .userId(1L)
                .productId(2L)
                .quantity(3)
                .build();

        Purchase savedPurchase = Purchase.builder()
                .user(user)
                .product(product)
                .quantity(3)
                .unitPrice(product.getPrice())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(3)))
                .status(PurchaseStatus.COMPLETED)
                .build();

        userJpaRepositoryStub(request.getUserId());
        productRepositoryStub(request.getProductId());

        org.mockito.Mockito.when(purchaseRepository.save(any(Purchase.class)))
                .thenReturn(savedPurchase);

        // when
        PurchaseResponse response = purchaseService.placePurchase(request);

        // then
        ArgumentCaptor<Purchase> purchaseCaptor = ArgumentCaptor.forClass(Purchase.class);
        org.mockito.Mockito.verify(purchaseRepository).save(purchaseCaptor.capture());

        assertThat(product.getStock()).isEqualTo(7);
        assertThat(response.getStatus()).isEqualTo(PurchaseStatus.COMPLETED);
        assertThat(response.getQuantity()).isEqualTo(3);
        assertThat(purchaseCaptor.getValue().getTotalPrice())
                .isEqualByComparingTo(savedPurchase.getTotalPrice());
    }

    @Test
    void placePurchase_insufficientStock_throwsExceptionAndDoesNotSave() {
        // given
        PurchaseRequest request = PurchaseRequest.builder()
                .userId(1L)
                .productId(2L)
                .quantity(11)
                .build();

        userJpaRepositoryStub(request.getUserId());
        productRepositoryStub(request.getProductId());

        // when & then
        assertThatThrownBy(() -> purchaseService.placePurchase(request))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining(ServiceExceptionCode.INSUFFICIENT_PRODUCT_STOCK.getMessage());

        assertThat(product.getStock()).isEqualTo(10);
        org.mockito.Mockito.verify(purchaseRepository, org.mockito.Mockito.never()).save(any(Purchase.class));
    }

    private void userJpaRepositoryStub(Long userId) {
        org.mockito.Mockito.when(userJpaRepository.findById(userId))
                .thenReturn(Optional.of(user));
    }

    private void productRepositoryStub(Long productId) {
        org.mockito.Mockito.when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
    }
}
