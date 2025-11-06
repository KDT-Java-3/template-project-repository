package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.domain.product.Product;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.CategoryRepository;
import com.sparta.demo.repository.ProductRepository;
import com.sparta.demo.service.dto.product.CategoryInfo;
import com.sparta.demo.service.dto.product.ProductCreateDto;
import com.sparta.demo.service.dto.product.ProductDto;
import com.sparta.demo.service.dto.product.ProductUpdateDto;
import com.sparta.demo.service.mapper.ProductServiceMapper;
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
 * ProductService 단위 테스트
 * Mockito를 사용하여 Repository와 Mapper를 Mock 처리
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductServiceMapper mapper;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 생성 성공")
    void createProduct_Success() {
        // Given: 상품 생성 요청 데이터와 카테고리가 주어졌을 때
        ProductCreateDto createDto = new ProductCreateDto(
                "노트북",
                "고성능 게이밍 노트북",
                new BigDecimal("1500000"),
                10,
                1L
        );

        Category category = Category.builder()
                .id(1L)
                .name("전자제품")
                .build();

        Product savedProduct = Product.builder()
                .id(1L)
                .name("노트북")
                .description("고성능 게이밍 노트북")
                .price(new BigDecimal("1500000"))
                .stock(10)
                .category(category)
                .build();

        ProductDto expectedDto = new ProductDto(
                1L,
                "노트북",
                "고성능 게이밍 노트북",
                new BigDecimal("1500000"),
                10,
                1L,
                "전자제품",
                Arrays.asList(new CategoryInfo(1L, "전자제품")),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(productRepository.save(any(Product.class))).willReturn(savedProduct);
        given(mapper.toDto(savedProduct)).willReturn(expectedDto);

        // When: 상품 생성 메서드를 호출하면
        ProductDto result = productService.createProduct(createDto);

        // Then: 상품이 정상적으로 생성되고 DTO가 반환된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("노트북");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("1500000"));
        assertThat(result.getStock()).isEqualTo(10);

        // 검증: 각 메서드가 올바르게 호출되었는지 확인
        verify(categoryRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
        verify(mapper).toDto(savedProduct);
    }

    @Test
    @DisplayName("상품 생성 실패 - 카테고리 없음")
    void createProduct_CategoryNotFound_ThrowsException() {
        // Given: 존재하지 않는 카테고리 ID로 상품 생성 요청이 주어졌을 때
        ProductCreateDto createDto = new ProductCreateDto(
                "노트북",
                "고성능 게이밍 노트북",
                new BigDecimal("1500000"),
                10,
                999L  // 존재하지 않는 카테고리 ID
        );

        // Mock 동작 설정: 카테고리를 찾을 수 없음
        given(categoryRepository.findById(999L)).willReturn(Optional.empty());

        // When & Then: 상품 생성 시도 시 CATEGORY_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> productService.createProduct(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.CATEGORY_NOT_FOUND);

        // 검증: 카테고리 조회만 수행되고 상품은 저장되지 않음
        verify(categoryRepository).findById(999L);
    }

    @Test
    @DisplayName("상품 조회 성공")
    void getProduct_Success() {
        // Given: 존재하는 상품 ID가 주어졌을 때
        Long productId = 1L;

        Category category = Category.builder()
                .id(1L)
                .name("전자제품")
                .build();

        Product product = Product.builder()
                .id(productId)
                .name("노트북")
                .description("고성능 게이밍 노트북")
                .price(new BigDecimal("1500000"))
                .stock(10)
                .category(category)
                .build();

        ProductDto expectedDto = new ProductDto(
                1L,
                "노트북",
                "고성능 게이밍 노트북",
                new BigDecimal("1500000"),
                10,
                1L,
                "전자제품",
                Arrays.asList(new CategoryInfo(1L, "전자제품")),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(mapper.toDto(product)).willReturn(expectedDto);

        // When: 상품 조회 메서드를 호출하면
        ProductDto result = productService.getProduct(productId);

        // Then: 상품 정보가 정상적으로 반환된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productId);
        assertThat(result.getName()).isEqualTo("노트북");

        verify(productRepository).findById(productId);
        verify(mapper).toDto(product);
    }

    @Test
    @DisplayName("상품 조회 실패 - 상품 없음")
    void getProduct_ProductNotFound_ThrowsException() {
        // Given: 존재하지 않는 상품 ID가 주어졌을 때
        Long productId = 999L;

        // Mock 동작 설정: 상품을 찾을 수 없음
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // When & Then: 상품 조회 시도 시 PRODUCT_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> productService.getProduct(productId))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.PRODUCT_NOT_FOUND);

        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProduct_Success() {
        // Given: 상품 수정 요청 데이터가 주어졌을 때
        Long productId = 1L;
        ProductUpdateDto updateDto = new ProductUpdateDto(
                "노트북 Pro",
                "업그레이드된 게이밍 노트북",
                new BigDecimal("2000000"),
                20,
                1L
        );

        Category category = Category.builder()
                .id(1L)
                .name("전자제품")
                .build();

        Product existingProduct = Product.builder()
                .id(productId)
                .name("노트북")
                .description("고성능 게이밍 노트북")
                .price(new BigDecimal("1500000"))
                .stock(10)
                .category(category)
                .build();

        ProductDto expectedDto = new ProductDto(
                productId,
                "노트북 Pro",
                "업그레이드된 게이밍 노트북",
                new BigDecimal("2000000"),
                20,
                1L,
                "전자제품",
                Arrays.asList(new CategoryInfo(1L, "전자제품")),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(productRepository.findById(productId)).willReturn(Optional.of(existingProduct));
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        given(mapper.toDto(existingProduct)).willReturn(expectedDto);

        // When: 상품 수정 메서드를 호출하면
        ProductDto result = productService.updateProduct(productId, updateDto);

        // Then: 상품 정보가 정상적으로 수정되고 반환된다
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("노트북 Pro");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("2000000"));

        verify(productRepository).findById(productId);
        verify(categoryRepository).findById(1L);
        verify(mapper).toDto(existingProduct);
    }

    @Test
    @DisplayName("상품 삭제 성공")
    void deleteProduct_Success() {
        // Given: 존재하는 상품 ID가 주어졌을 때
        Long productId = 1L;

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

        // Mock 동작 설정
        given(productRepository.existsById(productId)).willReturn(true);

        // When: 상품 삭제 메서드를 호출하면
        productService.deleteProduct(productId);

        // Then: 상품이 정상적으로 삭제된다
        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }
}
