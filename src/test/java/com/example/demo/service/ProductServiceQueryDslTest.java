package com.example.demo.service;

import com.example.demo.controller.dto.ProductResponseDto;
import com.example.demo.controller.dto.ProductSummaryResponseDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductSearchCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 실습: QueryDSL 적용된 ProductService 동작 검증 테스트.
 */
@SpringBootTest
@Transactional
class ProductServiceQueryDslTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category electronics;
    private Category kitchen;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        electronics = categoryRepository.save(Category.builder()
                .name("전자기기")
                .build());
        kitchen = categoryRepository.save(Category.builder()
                .name("주방용품")
                .build());

        productRepository.save(Product.builder()
                .category(electronics)
                .name("전자기기-노트북")
                .description("게이밍 노트북")
                .price(BigDecimal.valueOf(1_500_000))
                .stock(5)
                .build());

        productRepository.save(Product.builder()
                .category(electronics)
                .name("전자기기-마우스")
                .description("무선 마우스")
                .price(BigDecimal.valueOf(30_000))
                .stock(50)
                .build());

        productRepository.save(Product.builder()
                .category(kitchen)
                .name("주방용품-믹서기")
                .description("다기능 믹서기")
                .price(BigDecimal.valueOf(70_000))
                .stock(15)
                .build());
    }

    @Test
    @DisplayName("실습: 이름과 최소 가격 조건으로 상품을 필터링한다")
    void searchProductsWithDynamicConditions() {
        ProductSearchCondition condition = ProductSearchCondition.builder()
                .name("전자기기")
                .minPrice(BigDecimal.valueOf(100_000))
                .build();

        List<ProductResponseDto> result = productService.search(condition);

        assertThat(result)
                .extracting(ProductResponseDto::getName)
                .containsExactly("전자기기-노트북");
    }

    @Test
    @DisplayName("실습: 페이지네이션과 함께 동적 검색을 수행한다")
    void searchProductsWithPagination() {
        ProductSearchCondition condition = ProductSearchCondition.builder()
                .name("전자기기")
                .build();

        Page<ProductResponseDto> page = productService.search(condition, PageRequest.of(0, 1));

        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName()).contains("전자기기");
    }

    @Test
    @DisplayName("실습: 카테고리별 상품 요약 DTO를 조회한다")
    void getCategorySummaries() {
        List<ProductSummaryResponseDto> summaries = productService.getCategorySummaries(electronics.getId());

        assertThat(summaries).hasSize(2);
        assertThat(summaries)
                .extracting(ProductSummaryResponseDto::getCategoryName)
                .containsOnly("전자기기");
    }
}
