package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.DataReferencedException;
import com.sprata.sparta_ecommerce.controller.exception.DuplicationException;
import com.sprata.sparta_ecommerce.dto.*;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.entity.Order;
import com.sprata.sparta_ecommerce.entity.Product;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CategoryServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;


    private CategoryRequestDto parentRequest;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setup() {
        parentRequest = new CategoryRequestDto();
        parentRequest.setName("전자제품");
        parentRequest.setDescription("전자 관련 제품 카테고리");
        parentRequest.setParent_id(0L);
    }


    @Test
    @DisplayName("✅ 카테고리 등록 성공")
    void addCategory_success() {
        // when
        CategoryResponseDto result = categoryService.addCategory(parentRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("전자제품");
        assertThat(categoryRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("❌ 중복 카테고리 등록 실패")
    void addCategory_fail_duplicate() {
        // given
        categoryService.addCategory(parentRequest);

        // when & then
        assertThatThrownBy(() -> categoryService.addCategory(parentRequest))
                .isInstanceOf(DuplicationException.class)
                .hasMessageContaining("이미 존재하는 카테고리입니다");
    }

    // -----------------------------
    // UPDATE TESTS
    // -----------------------------
    @Test
    @DisplayName("✅ 카테고리 수정 성공")
    void updateCategory_success() {
        // given
        Category saved = categoryRepository.save(Category.builder()
                .name("의류")
                .description("옷 카테고리")
                .build());

        CategoryRequestDto updateDto = new CategoryRequestDto();
        updateDto.setName("패션");
        updateDto.setDescription("의류 및 패션 관련");
        updateDto.setParent_id(0L);

        // when
        CategoryResponseDto updated = categoryService.updateCategory(saved.getId(), updateDto);

        // then
        assertThat(updated.getName()).isEqualTo("패션");
        assertThat(updated.getDescription()).isEqualTo("의류 및 패션 관련");
    }

    @Test
    @DisplayName("❌ 카테고리 수정 실패 - 존재하지 않는 ID")
    void updateCategory_fail_notFound() {
        // given
        CategoryRequestDto updateDto = new CategoryRequestDto();
        updateDto.setName("가전");
        updateDto.setDescription("수정 실패 테스트");

        // when & then
        assertThatThrownBy(() -> categoryService.updateCategory(999L, updateDto))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("찾을 수 없습니다");
    }

    @Test
    @DisplayName("❌ 카테고리 수정 실패 - 중복 이름")
    void updateCategory_fail_duplicateName() {
        // given
        Category cat1 = categoryRepository.save(Category.builder().name("식품").description("desc").build());
        Category cat2 = categoryRepository.save(Category.builder().name("전자").description("desc").build());

        CategoryRequestDto updateDto = new CategoryRequestDto();
        updateDto.setName("식품"); // 이미 존재

        // when & then
        assertThatThrownBy(() -> categoryService.updateCategory(cat2.getId(), updateDto))
                .isInstanceOf(DuplicationException.class)
                .hasMessageContaining("이미 존재하는 카테고리명");
    }

    @Test
    @DisplayName("❌ 카테고리 수정 실패 - 상위 카테고리 없음")
    void updateCategory_fail_parentNotFound() {
        // given: 기존 카테고리 생성
        CategoryResponseDto categoryResponseDto = categoryService.addCategory(parentRequest);

        // 부모 카테고리 없는 ID 지정
        CategoryRequestDto updateDto = new CategoryRequestDto();
        updateDto.setName("가전제품");
        updateDto.setDescription("수정된 설명");
        updateDto.setParent_id(999L); // 존재하지 않는 상위 카테고리

        // when & then
        assertThatThrownBy(() -> categoryService.updateCategory(categoryResponseDto.getId(), updateDto))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("상위 카테고리를 찾을 수 없습니다");
    }

    // -----------------------------
    // GET ALL TESTS
    // -----------------------------
    @Test
    @DisplayName("✅ Top 10 카테고리 목록 조회 성공")
    void getTop10SalesCategories_success() {
        // given: 카테고리 12개 생성
        List<CategoryResponseDto> categoryDtos = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            categoryDtos.add(
                    categoryService.addCategory(
                            new CategoryRequestDto("카테고리" + i, "설명" + i, 0L)
                    )
            );
        }

        // 상품 생성 및 주문 생성
        for (int i = 0; i < categoryDtos.size(); i++) {
            // 상품 생성
            ProductResponseDto productDto = productService.addProduct(
                    ProductServiceInputDto.builder()
                            .name("상품" + (i + 1))
                            .description("설명" + (i + 1))
                            .price(1000L * (i + 1))
                            .stock(100)
                            .category_id(categoryDtos.get(i).getId())
                            .build()
            );

            // 주문 생성 (판매량 차등)
            orderService.createOrder(
                    new OrderRequestDto(
                            1L,                     // 사용자 ID
                            productDto.getId(),     // 상품 ID
                            i + 1,                  // 수량: 1~12
                            "주소 " + (i + 1)       // 배송지
                    )
            );
        }

        // when: Top 10 조회
        List<CategoryDetailResponseDto> result = categoryService.getTop10SalesCategories();

        // then: Top 10만 반환
        assertThat(result).hasSize(10);

        // 가장 많이 팔린 카테고리 확인 - 첫 번째 카테고리 확인
        assertThat(result.get(0).getName()).isEqualTo("카테고리12");
        // 마지막(10번째) 카테고리 확인
        assertThat(result.get(9).getSalesCount()).isEqualTo(3);
        assertThat(result.get(9).getName()).isEqualTo("카테고리3");

    }


    // -----------------------------
    // 하위 카테고리 생성
    // -----------------------------
    @Test
    @DisplayName("하위 카테고리 생성 성공")
    void addSubCategorySuccess() {
        // 먼저 상위 카테고리 생성
        CategoryRequestDto parentRequest = new CategoryRequestDto();
        parentRequest.setName("가전");
        parentRequest.setDescription("가전 제품");
        parentRequest.setParent_id(0L);
        CategoryResponseDto parentCategory = categoryService.addCategory(parentRequest);

        // 하위 카테고리 생성
        CategoryRequestDto childRequest = new CategoryRequestDto();
        childRequest.setName("TV");
        childRequest.setDescription("텔레비전");
        childRequest.setParent_id(parentCategory.getId());

        CategoryResponseDto childCategory = categoryService.addCategory(childRequest);

        assertThat(childCategory.getId()).isNotNull();
        assertThat("TV").isEqualTo(childCategory.getName());
        assertThat(parentCategory.getName()).isEqualTo(childCategory.getParentCategoryName());
    }

    @Test
    @DisplayName("상위 카테고리가 없으면 예외 발생")
    void addSubCategoryParentNotFound() {
        CategoryRequestDto childRequest = new CategoryRequestDto();
        childRequest.setName("TV");
        childRequest.setDescription("텔레비전");
        childRequest.setParent_id(999L); // 존재하지 않는 상위 카테고리


        // when & then
        assertThatThrownBy(() -> categoryService.addCategory(childRequest))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("상위 카테고리를 찾을 수 없습니다");
    }

    // ✅ 성공 케이스
    @Test
    @DisplayName("✅ 카테고리 삭제 성공 - 하위, 상품 없음")
    void deleteCategory_success() {
        // given
        Category removable = categoryRepository.save(
                Category.builder().name("삭제대상").description("삭제용").build()
        );

        // when & then
        assertThatCode(() -> categoryService.deleteCategory(removable.getId()))
                .doesNotThrowAnyException();

        assertThat(categoryRepository.findById(removable.getId())).isEmpty();
    }

    // ❌ 실패 1 - 하위 카테고리 존재
    @Test
    @DisplayName("❌ 카테고리 삭제 실패 - 하위 카테고리 존재")
    void deleteCategory_fail_hasSubCategory() {

        Category parentCategory = categoryRepository.save(
                Category.builder().name("전자제품").description("전자 관련").build()
        );
        Category childCategory = categoryRepository.save(
                Category.builder().name("가전소형").description("소형 전자").parentCategory(parentCategory).build()
        );

        em.flush();
        em.clear();

        assertThatThrownBy(() -> categoryService.deleteCategory(parentCategory.getId()))
                .isInstanceOf(DataReferencedException.class)
                .hasMessageContaining("하위 카테고리가 존재합니다.");
    }

    // ❌ 실패 2 - 연관 상품 존재
    @Test
    @DisplayName("❌ 카테고리 삭제 실패 - 연관 상품 존재")
    void deleteCategory_fail_hasProduct() {
        // given
        Category parentCategory = categoryRepository.save(
                Category.builder().name("전자제품").description("전자 관련").build()
        );

        em.flush();
        em.clear();

        productService.addProduct(ProductServiceInputDto.builder()
                .name("노트북")
                .description("테스트 상품")
                .price(1000L)
                .stock(5)
                .category_id(parentCategory.getId())
                .build());

        // when & then
        assertThatThrownBy(() -> categoryService.deleteCategory(parentCategory.getId()))
                .isInstanceOf(DataReferencedException.class)
                .hasMessageContaining("연관된 상품이 존재합니다.");
    }

    // ❌ 실패 3 - 존재하지 않는 카테고리
    @Test
    @DisplayName("❌ 카테고리 삭제 실패 - 존재하지 않는 카테고리")
    void deleteCategory_fail_notFound() {
        assertThatThrownBy(() -> categoryService.deleteCategory(999L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("해당 카테고리를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("✅ 카테고리 트리 조회 성공")
    void getCategoryTree_success() {
        // given
        // 1. 루트 카테고리 2개 생성
        CategoryResponseDto categoryResponseDto = categoryService.addCategory(CategoryRequestDto.builder().name("전자제품").description("").parent_id(0L).build());
        CategoryResponseDto categoryResponseDto1 = categoryService.addCategory(CategoryRequestDto.builder().name("전자제품1").description("").parent_id(0L).build());
        categoryService.addCategory(CategoryRequestDto.builder().name("패션").description("뿡").parent_id(0L).build());


        // 2. 하위 카테고리 생성
        CategoryResponseDto categoryResponseDto2 = categoryService.addCategory(CategoryRequestDto.builder().name("서브전자제품1").description("").parent_id(categoryResponseDto.getId()).build());
        categoryService.addCategory(CategoryRequestDto.builder().name("서브전자제품2").description("").parent_id(categoryResponseDto.getId()).build());
        categoryService.addCategory(CategoryRequestDto.builder().name("서브전자제품3").description("").parent_id(categoryResponseDto1.getId()).build());

        // 3. 손자 카테고리 생성
        categoryService.addCategory(CategoryRequestDto.builder().name("손자전자제품1").description("").parent_id(categoryResponseDto2.getId()).build());
        categoryService.addCategory(CategoryRequestDto.builder().name("손자전자제품2").description("").parent_id(categoryResponseDto2.getId()).build());
        categoryService.addCategory(CategoryRequestDto.builder().name("손자전자제품3").description("").parent_id(categoryResponseDto2.getId()).build());

        em.flush();
        em.clear();

        // when
        List<CategoryTreeResponseDto> categoryTree = categoryService.getCategoryTree();

        // then
        // ✅ 1. 루트 카테고리는 3개 (전자제품, 전자제품1, 패션)
        assertThat(categoryTree).hasSize(3)
                .extracting("name")
                .containsExactlyInAnyOrder("전자제품", "전자제품1", "패션");

        // ✅ 2. "전자제품" → 자식 2개
        CategoryTreeResponseDto electronics = categoryTree.stream()
                .filter(c -> c.getName().equals("전자제품"))
                .findFirst().orElseThrow();
        assertThat(electronics.getChildren())
                .hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("서브전자제품1", "서브전자제품2");

        // ✅ 3. "서브전자제품1" → 손자 3개
        CategoryTreeResponseDto subElectronics1 = electronics.getChildren().stream()
                .filter(c -> c.getName().equals("서브전자제품1"))
                .findFirst().orElseThrow();
        assertThat(subElectronics1.getChildren())
                .hasSize(3)
                .extracting("name")
                .containsExactlyInAnyOrder("손자전자제품1", "손자전자제품2", "손자전자제품3");

        // ✅ 4. "전자제품1" → 자식 1개
        CategoryTreeResponseDto electronics1 = categoryTree.stream()
                .filter(c -> c.getName().equals("전자제품1"))
                .findFirst().orElseThrow();
        assertThat(electronics1.getChildren())
                .hasSize(1)
                .extracting("name")
                .containsExactly("서브전자제품3");

        // ✅ 5. "패션" → 자식 없음
        CategoryTreeResponseDto fashion = categoryTree.stream()
                .filter(c -> c.getName().equals("패션"))
                .findFirst().orElseThrow();
        assertThat(fashion.getChildren()).isEmpty();
    }
}
