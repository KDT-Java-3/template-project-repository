package com.sparta.jc.domain.product.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.jc.domain.category.entity.Category;
import com.sparta.jc.domain.category.repository.CategoryRepository;
import com.sparta.jc.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ProductController 통합 테스트
 * 실제 DB(H2)와 연동하여 CRUD API를 검증
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 테스트 순서 지정 (등록 → 조회 → 수정)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // HTTP 요청 시뮬레이터

    @Autowired
    private ObjectMapper objectMapper; // JSON 직렬화용

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static Long categoryId; // 테스트용 카테고리 ID
    private static Long createdProductId; // 생성된 상품 ID 저장용

    @BeforeEach
    void setup() {
        // H2 DB 초기화 전용
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        // 테스트용 카테고리 생성
        Category category = Category.builder()
                .name("전자제품")
                .build();

        categoryId = categoryRepository.save(category).getId();
    }

    /**
     * 상품 등록 테스트
     */
    @Test
    @Order(1)
    void createProductTest() throws Exception {
        // given: 요청 바디 구성
        var request = new ProductRequest(
                "테스트 TV",
                "65인치 OLED TV",
                BigDecimal.valueOf(1500000),
                10,
                categoryId
        );

        // when + then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("테스트 TV"))
                .andExpect(jsonPath("$.price").value(1500000))
                .andDo(result -> {
                    // 생성된 ID 추출
                    String json = result.getResponse().getContentAsString();
                    createdProductId = objectMapper.readTree(json).get("id").asLong();
                });
    }

    /**
     * 단일 상품 조회 테스트
     */
    @Test
    @Order(2)
    void getProductTest() throws Exception {
        // 사전조건: 상품 등록
        var request = new ProductRequest("테스트 상품", "설명", BigDecimal.valueOf(1500000), 5, categoryId);
        var productJson = objectMapper.writeValueAsString(request);

        String responseJson = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andReturn().getResponse().getContentAsString();
        Long productId = objectMapper.readTree(responseJson).get("id").asLong();

        // when + then
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("테스트 상품"))
                .andExpect(jsonPath("$.categoryName").value("전자제품"));
    }
//
//    /**
//     * 상품 목록 조회 테스트 (검색/필터링 포함)
//     */
//    @Test
//    @Order(3)
//    void getProductListTest() throws Exception {
//        // given: 테스트 데이터 여러 개 삽입
//        for (int i = 1; i <= 3; i++) {
//            var req = new ProductRequest("상품" + i, "설명", 1000 * i, 10, categoryId);
//            mockMvc.perform(post("/api/products")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(req)));
//        }
//
//        // when + then: 상품명 "상품1" 검색
//        mockMvc.perform(get("/api/products")
//                        .param("keyword", "상품1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(1)))
//                .andExpect(jsonPath("$.content[0].name").value("상품1"));
//    }
//
//    /**
//     * 상품 수정 테스트
//     */
//    @Test
//    @Order(4)
//    void updateProductTest() throws Exception {
//        // 사전조건: 등록
//        var request = new ProductRequest("노트북", "게이밍 노트북", 2000000, 5, categoryId);
//        String responseJson = mockMvc.perform(post("/api/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andReturn().getResponse().getContentAsString();
//        Long productId = objectMapper.readTree(responseJson).get("id").asLong();
//
//        // 수정 요청 데이터
//        var updateRequest = new ProductRequest("노트북 PRO", "하이엔드 게이밍 노트북", 2500000, 8, categoryId);
//
//        // when + then
//        mockMvc.perform(put("/api/products/{id}", productId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("노트북 PRO"))
//                .andExpect(jsonPath("$.price").value(2500000))
//                .andExpect(jsonPath("$.stock").value(8));
//    }
}
