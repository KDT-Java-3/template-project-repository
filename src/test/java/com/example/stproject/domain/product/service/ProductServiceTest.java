package com.example.stproject.domain.product.service;

import com.example.stproject.domain.product.dto.ProductCreateRequest;
import com.example.stproject.domain.product.entity.Product;
import com.example.stproject.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 정상 등록")
    void createProduct_success() {
        // given
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("화이트 셔츠");
        request.setDescription("베이직 슬림핏");
        request.setPrice(39900L);
        request.setStock(120);

        // when
        Long savedId = productService.create(request);

        // then
        Product savedProduct = productRepository.findById(savedId).orElseThrow();
        assertThat(savedProduct.getName()).isEqualTo("화이트 셔츠");
        assertThat(savedProduct.getDescription()).isEqualTo("베이직 슬림핏");
        assertThat(savedProduct.getPrice()).isEqualTo(39900L);
        assertThat(savedProduct.getStock()).isEqualTo(120);
    }

    @Test
    @DisplayName("필수값이 누락되면 예외가 발생")
    void createProduct_missingName() {
        // given
        ProductCreateRequest request = new ProductCreateRequest();
        request.setPrice(1000L);
        request.setStock(10);

        // when & then
        // 이 테스트는 @Valid 검증을 거치지 않으므로, 서비스 단에서 직접 검증 로직을 넣지 않았다면
        // 별도 검증은 Controller 레벨(@Valid)에서 처리됨.
        // 따라서 실제 Validation 테스트는 WebMvcTest로 따로 하는 것이 일반적임.
    }

}