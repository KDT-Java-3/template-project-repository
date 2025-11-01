package com.sprata.sparta_ecommerce;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.dto.ProductRequestDto;
import com.sprata.sparta_ecommerce.dto.ProductResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ProductServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    //
    private Category electronics;
    private Category furniture;

    Product samsungNoteBook;
    Product lgTv;
    Product desk;
    Product chair;

    ProductRequestDto productRequestDto;


    @BeforeEach
    void setUp() {
        // 카테고리 저장
        electronics = categoryRepository.save(new Category("가전", "전자제품"));
        furniture = categoryRepository.save(new Category("가구", "가구류"));

        this.productRequestDto =  new ProductRequestDto(
                "아이폰", "애플폰이야", 132_000_000L, 10, electronics.getId()
        );

        // 상품 저장
        this.samsungNoteBook = productRepository.save(new Product("삼성 노트북", "노트북", 1500L, 10, electronics));
        this.lgTv = productRepository.save(new Product("LG TV", "42인치 TV", 1200L, 5, electronics));
        this.desk = productRepository.save(new Product("책상", "원목 책상", 300L, 3, furniture));
        this.chair = productRepository.save(new Product("의자", "사무용 의자", 100L, 7, furniture));
    }


    @Test
    @DisplayName("상품 생성 성공")
    void addProductSuccess() {
        ProductResponseDto responseDto = productService.addProduct(productRequestDto);

        assertNotNull(responseDto.getId());
        assertEquals("아이폰", responseDto.getName());
        assertEquals(electronics.getId(), responseDto.getCategory_id());
    }

    @Test
    @DisplayName("상품 생성 실패 - 카테고리 없음")
    void addProductFailCategoryNotFound() {
        productRequestDto.setCategory_id(999L); // 존재하지 않는 카테고리

        DataNotFoundException ex = assertThrows(DataNotFoundException.class,
                () -> productService.addProduct(productRequestDto));

        assertEquals("해당 카테고리를 찾을 수 없습니다.", ex.getMessage());
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProductSuccess() {



        // 수정 DTO
        ProductRequestDto updateDto = new ProductRequestDto();
        updateDto.setName("LG TV");
        updateDto.setDescription("42인치 TV");
        updateDto.setPrice(1200L);
        updateDto.setStock(5);
        updateDto.setCategory_id(electronics.getId());

        ProductResponseDto updated = productService.updateProduct(lgTv.getId(), updateDto);

        assertEquals("LG TV", updated.getName());
        assertEquals(1200L, updated.getPrice());
        assertEquals(5, updated.getStock());
    }

    @Test
    @DisplayName("상품 수정 실패 - 상품 없음")
    void updateProductFailNotFound() {
        ProductRequestDto updateDto = new ProductRequestDto();
        updateDto.setName("LG TV");
        updateDto.setDescription("42인치 TV");
        updateDto.setPrice(1200L);
        updateDto.setStock(5);
        updateDto.setCategory_id(electronics.getId());

        DataNotFoundException ex = assertThrows(DataNotFoundException.class,
                () -> productService.updateProduct(999L, updateDto));

        assertEquals("해당 상품을 찾을 수 없습니다.", ex.getMessage());
    }

    @Test
    @DisplayName("상품 수정 실패 - 카테고리 없음")
    void updateProductFailCategoryNotFound() {
        ProductResponseDto created = productService.addProduct(productRequestDto);

        ProductRequestDto updateDto = new ProductRequestDto();
        updateDto.setName("LG TV");
        updateDto.setDescription("42인치 TV");
        updateDto.setPrice(1200L);
        updateDto.setStock(5);
        updateDto.setCategory_id(999L); // 존재하지 않는 카테고리

        DataNotFoundException ex = assertThrows(DataNotFoundException.class,
                () -> productService.updateProduct(created.getId(), updateDto));

        assertEquals("해당 카테고리를 찾을 수 없습니다.", ex.getMessage());
    }

    // ----------------------------
    // 전체 조회 (조건 없음)
    // ----------------------------
    @Test
    @DisplayName("전체 상품 조회")
    void getAllProductsNoCondition() {
        SearchProductDto searchDto = SearchProductDto.builder().build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertEquals(4, results.size());
    }

    // ----------------------------
    // 카테고리 조건 조회
    // ----------------------------
    @Test
    @DisplayName("카테고리 조건 조회")
    void getAllProductsByCategory() {
        SearchProductDto searchDto = SearchProductDto.builder()
                .categoryId(electronics.getId())
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(p -> p.getCategory_id().equals(electronics.getId())));
    }

    // ----------------------------
    // 가격 범위 조건 조회
    // ----------------------------
    @Test
    @DisplayName("가격 범위 조건 조회")
    void getAllProductsByPriceRange() {
        SearchProductDto searchDto = SearchProductDto.builder()
                .minPrice(200L)
                .maxPrice(1300L)
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(p -> p.getPrice() >= 200L && p.getPrice() <= 1300L));
    }

    // ----------------------------
    // 키워드 조건 조회
    // ----------------------------
    @Test
    @DisplayName("키워드 조건 조회")
    void getAllProductsByKeyword() {
        SearchProductDto searchDto = SearchProductDto.builder()
                .keyword("노트북")
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertEquals(1, results.size());
        assertEquals("삼성 노트북", results.get(0).getName());
    }

    // ----------------------------
    // 카테고리 + 가격 + 키워드 복합 조건 조회
    // ----------------------------
    @Test
    @DisplayName("복합 조건 조회")
    void getAllProductsByMultipleConditions() {
        SearchProductDto searchDto = SearchProductDto.builder()
                .categoryId(furniture.getId())
                .minPrice(50L)
                .maxPrice(200L)
                .keyword("의자")
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertEquals(1, results.size());
        assertEquals("의자", results.get(0).getName());
    }

    // ----------------------------
    // 검색 조건 결과 없음
    // ----------------------------
    @Test
    @DisplayName("조건 만족하는 상품 없음")
    void getAllProductsNoMatch() {
        SearchProductDto searchDto = SearchProductDto.builder()
                .categoryId(electronics.getId())
                .minPrice(2000L)
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertTrue(results.isEmpty());
    }
}
