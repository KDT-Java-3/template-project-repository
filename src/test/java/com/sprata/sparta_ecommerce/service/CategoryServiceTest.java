package com.sprata.sparta_ecommerce.service;

import com.sprata.sparta_ecommerce.controller.exception.DataNotFoundException;
import com.sprata.sparta_ecommerce.controller.exception.DuplicationException;
import com.sprata.sparta_ecommerce.dto.CategoryRequestDto;
import com.sprata.sparta_ecommerce.dto.CategoryResponseDto;
import com.sprata.sparta_ecommerce.dto.param.PageDto;
import com.sprata.sparta_ecommerce.entity.Category;
import com.sprata.sparta_ecommerce.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CategoryServiceTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryRepository categoryRepository;


    private CategoryRequestDto parentRequest;

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
    @DisplayName("✅ 카테고리 목록 조회 성공")
    void getAllCategories_success() {
        // given
        categoryRepository.save(Category.builder().name("식품").description("desc").build());
        categoryRepository.save(Category.builder().name("패션").description("desc").build());
        categoryRepository.save(Category.builder().name("전자").description("desc").build());

        PageDto pageDto = new PageDto(1, 10);

        // when
        List<CategoryResponseDto> result = categoryService.getAllCategories(pageDto);

        // then
        assertThat(result).hasSize(3);
        assertThat(result).extracting(CategoryResponseDto::getName)
                .containsExactlyInAnyOrder("식품", "패션", "전자");
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
}