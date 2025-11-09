package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.DataReferencedException;
import com.sprata.sparta_ecommerce.controller.exception.DuplicationException;
import com.sprata.sparta_ecommerce.controller.mapper.ProductMapper;
import com.sprata.sparta_ecommerce.dto.ProductRequestDto;
import com.sprata.sparta_ecommerce.dto.ProductResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.dto.param.SearchProductDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.OrderStatus;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import com.sprata.sparta_ecommerce.repository.OrderRepository;
import com.sprata.sparta_ecommerce.repository.ProductRepository;
import com.sprata.sparta_ecommerce.service.dto.ProductServiceInputDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderRepository orderRepository;

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
        electronics = categoryRepository.save(new Category("가전", "전자제품",null));
        furniture = categoryRepository.save(new Category("가구", "가구류",null));

        this.productRequestDto =  new ProductRequestDto(
                "아이폰", "애플폰이야", 132_000_000L, 10, electronics.getId()
        );

        // 상품 저장
        this.samsungNoteBook = productRepository.save(new Product("삼성 노트북", "노트북", 1500L, 10, electronics));
        this.lgTv = productRepository.save(new Product("LG TV", "42인치 TV", 1200L, 5, electronics));
        this.desk = productRepository.save(new Product("책상", "원목 책상", 300L, 3, furniture));
        this.chair = productRepository.save(new Product("의자", "사무용 의자", 300L, 7, furniture));
    }


    @Test
    @DisplayName("상품 생성 성공")
    void addProductSuccess() {
        ProductServiceInputDto inputDto = productMapper.toService(productRequestDto);
        ProductResponseDto responseDto = productService.addProduct(inputDto);

        assertThat(responseDto.getId()).isNotNull();
        assertThat(responseDto.getName()).isEqualTo("아이폰");
        assertThat(responseDto.getCategory_id()).isEqualTo(electronics.getId());
    }

    @Test
    @DisplayName("상품 생성 실패 - 카테고리 없음")
    void addProductFailCategoryNotFound() {
        productRequestDto.setCategory_id(999L); // 존재하지 않는 카테고리

        ProductServiceInputDto inputDto = productMapper.toService(productRequestDto);
        assertThatThrownBy(() -> productService.addProduct(inputDto))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 카테고리를 찾을 수 없습니다.");
    }


    @Test
    @DisplayName("상품 생성 실패 - 상품 명 중복")
    void addProductFailDuplicateProductName() {
        Product product = productRepository.findAll().get(0);
        String duplicateName = product.getName();
        ProductRequestDto dto = new ProductRequestDto(
                duplicateName, "중복이름", 1_000_000L, 5, electronics.getId()
        );

        ProductServiceInputDto inputDto = productMapper.toService(dto);
        assertThatThrownBy(() -> productService.addProduct(inputDto))
                .isInstanceOf(DuplicationException.class)
                .hasMessage(duplicateName + " 중복된 상품명 존재합니다.");
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

        ProductServiceInputDto dto = productMapper.toService(updateDto);

        ProductResponseDto updated = productService.updateProduct(lgTv.getId(), dto);

        assertThat(updated.getName()).isEqualTo("LG TV");
        assertThat(updated.getPrice()).isEqualTo(1200L);
        assertThat(updated.getStock()).isEqualTo(5);
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

        ProductServiceInputDto dto = productMapper.toService(updateDto);

        assertThatThrownBy(() -> productService.updateProduct(999L, dto))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 수정 실패 - 카테고리 없음")
    void updateProductFailCategoryNotFound() {
        ProductServiceInputDto inputDto = productMapper.toService(productRequestDto);
        ProductResponseDto created = productService.addProduct(inputDto);

        ProductRequestDto updateDto = new ProductRequestDto();
        updateDto.setName("LG TV");
        updateDto.setDescription("42인치 TV");
        updateDto.setPrice(1200L);
        updateDto.setStock(5);
        updateDto.setCategory_id(999L); // 존재하지 않는 카테고리

        ProductServiceInputDto dto = productMapper.toService(updateDto);

        assertThatThrownBy(() -> productService.updateProduct(created.getId(), dto))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 카테고리를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("상품 수정 실패 - 상품 이름 중복")
    void updateProductDuplicateProductName() {
        ProductServiceInputDto inputDto = productMapper.toService(productRequestDto);
        ProductResponseDto created = productService.addProduct(inputDto);

        // 중복데이터생성
        Product product = productRepository.findAll().get(0);

        ProductRequestDto updateDto = new ProductRequestDto();
        updateDto.setName(product.getName());
        updateDto.setDescription("42인치 TV");
        updateDto.setPrice(1200L);
        updateDto.setStock(5);
        updateDto.setCategory_id(electronics.getId());

        ProductServiceInputDto dto = productMapper.toService(updateDto);

        assertThatThrownBy(() -> productService.updateProduct(created.getId(), dto))
                .isInstanceOf(DuplicationException.class)
                .hasMessage(product.getName() + " 중복된 상품명 존재합니다.");
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

        assertThat(results).hasSize(4);
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

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(p -> p.getCategory_id().equals(electronics.getId()));
    }

    // ----------------------------
    // 가격 범위 조건 조회
    // ----------------------------
    @Test
    @DisplayName("가격 범위 조건 조회")
    void getAllProductsByPriceRange() {
        SearchProductDto searchDto = SearchProductDto.builder()
                .minPrice(200L)
                .maxPrice(1000L)
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(p -> p.getPrice() >= 200L && p.getPrice() <= 1300L);
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

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("삼성 노트북");
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
                .maxPrice(1000L)
                .keyword("의자")
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("의자");
    }

    // ----------------------------
    // 가격 -> 이름 순으로 ASC 조회
    // ----------------------------
    @Test
    @DisplayName("복합 조건 조회")
    void getAllProductsByMultipleOrderBy() {
        SearchProductDto searchDto = SearchProductDto.builder()
                .sortConditions(List.of(
                        new SearchProductDto.SortCondition("price","desc")
                        ,new SearchProductDto.SortCondition("name","asc")
                ))
                .build();
        PageDto pageDto = new PageDto(1, 10);

        var results = productService.getAllProducts(searchDto, pageDto);
        assertThat(results).hasSize(4);
        assertThat(results.get(0).getName()).isEqualTo("삼성 노트북");
        assertThat(results.get(1).getName()).isEqualTo("LG TV");
        assertThat(results.get(2).getName()).isEqualTo("의자");
        assertThat(results.get(3).getName()).isEqualTo("책상");
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

        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("✅ 주문이 없는 상품은 정상 삭제된다")
    void deleteProduct_success() {
        // given
        Long productId = samsungNoteBook.getId();

        // when
        productService.deleteProduct(productId);
        em.flush();
        em.clear();

        // then
        assertThat(productRepository.findById(productId)).isEmpty();
    }

    @Test
    @DisplayName("❌ 주문이 존재하면 DataReferencedException 발생")
    void deleteProduct_fail_dueToOrderExists() {
        // given
        Long productId = samsungNoteBook.getId();
        Order order = orderRepository.save(new Order(1L, samsungNoteBook, 2, "주소", LocalDate.now()));

        // when & then
        order.updateStatus(OrderStatus.COMPLETED);
        em.flush();
        em.clear();

        // when
        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(DataReferencedException.class)
                .hasMessage(samsungNoteBook.getName() + " 은 주문이 존재합니다.");
    }

    @Test
    @DisplayName("❌ 존재하지 않는 상품 삭제 시 DataNotFoundException 발생")
    void deleteProduct_fail_notFound() {
        // given
        Long invalidProductId = 9999L;

        assertThatThrownBy(() -> productService.deleteProduct(invalidProductId))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("해당 상품을 찾을 수 없습니다.");

    }
}
