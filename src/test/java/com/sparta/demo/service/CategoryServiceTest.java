package com.sparta.demo.service;

import com.sparta.demo.domain.category.Category;
import com.sparta.demo.exception.ServiceException;
import com.sparta.demo.exception.ServiceExceptionCode;
import com.sparta.demo.repository.CategoryRepository;
import com.sparta.demo.service.dto.category.CategoryCreateDto;
import com.sparta.demo.service.dto.category.CategoryDto;
import com.sparta.demo.service.mapper.CategoryServiceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * CategoryService 단위 테스트
 * Mockito를 사용하여 Repository와 Mapper를 Mock 처리
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryServiceMapper mapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("최상위 카테고리 생성 성공")
    void createCategory_TopLevel_Success() {
        // Given: 부모 카테고리가 없는 최상위 카테고리 생성 요청이 주어졌을 때
        CategoryCreateDto createDto = new CategoryCreateDto(
                "전자제품",
                "전자제품 카테고리",
                null  // 부모 카테고리 없음
        );

        Category savedCategory = Category.builder()
                .id(1L)
                .name("전자제품")
                .description("전자제품 카테고리")
                .build();

        CategoryDto expectedDto = new CategoryDto(
                1L,
                "전자제품",
                "전자제품 카테고리",
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(categoryRepository.save(any(Category.class))).willReturn(savedCategory);
        given(mapper.toDto(savedCategory)).willReturn(expectedDto);

        // When: 카테고리 생성 메서드를 호출하면
        CategoryDto result = categoryService.createCategory(createDto);

        // Then: 최상위 카테고리가 정상적으로 생성된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("전자제품");
        assertThat(result.getParentId()).isNull();

        // 검증: 저장 메서드가 호출되었는지 확인
        verify(categoryRepository).save(any(Category.class));
        verify(mapper).toDto(savedCategory);
    }

    @Test
    @DisplayName("하위 카테고리 생성 성공")
    void createCategory_SubCategory_Success() {
        // Given: 부모 카테고리가 있는 하위 카테고리 생성 요청이 주어졌을 때
        Long parentId = 1L;
        CategoryCreateDto createDto = new CategoryCreateDto(
                "노트북",
                "노트북 카테고리",
                parentId
        );

        Category parentCategory = Category.builder()
                .id(parentId)
                .name("전자제품")
                .description("전자제품 카테고리")
                .build();

        Category savedCategory = Category.builder()
                .id(2L)
                .name("노트북")
                .description("노트북 카테고리")
                .parent(parentCategory)
                .build();

        CategoryDto expectedDto = new CategoryDto(
                2L,
                "노트북",
                "노트북 카테고리",
                parentId,
                "전자제품",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(categoryRepository.findById(parentId)).willReturn(Optional.of(parentCategory));
        given(categoryRepository.save(any(Category.class))).willReturn(savedCategory);
        given(mapper.toDto(savedCategory)).willReturn(expectedDto);

        // When: 하위 카테고리 생성 메서드를 호출하면
        CategoryDto result = categoryService.createCategory(createDto);

        // Then: 하위 카테고리가 정상적으로 생성된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("노트북");
        assertThat(result.getParentId()).isEqualTo(parentId);
        assertThat(result.getParentName()).isEqualTo("전자제품");

        // 검증: 부모 카테고리 조회 및 저장이 호출되었는지 확인
        verify(categoryRepository).findById(parentId);
        verify(categoryRepository).save(any(Category.class));
        verify(mapper).toDto(savedCategory);
    }

    @Test
    @DisplayName("하위 카테고리 생성 실패 - 부모 카테고리 없음")
    void createCategory_ParentNotFound_ThrowsException() {
        // Given: 존재하지 않는 부모 카테고리 ID로 하위 카테고리 생성 요청이 주어졌을 때
        Long parentId = 999L;
        CategoryCreateDto createDto = new CategoryCreateDto(
                "노트북",
                "노트북 카테고리",
                parentId
        );

        // Mock 동작 설정: 부모 카테고리를 찾을 수 없음
        given(categoryRepository.findById(parentId)).willReturn(Optional.empty());

        // When & Then: 카테고리 생성 시도 시 PARENT_CATEGORY_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> categoryService.createCategory(createDto))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.PARENT_CATEGORY_NOT_FOUND);

        // 검증: 부모 카테고리 조회만 수행되고 저장은 되지 않음
        verify(categoryRepository).findById(parentId);
    }

    @Test
    @DisplayName("카테고리 조회 성공")
    void getCategory_Success() {
        // Given: 존재하는 카테고리 ID가 주어졌을 때
        Long categoryId = 1L;

        Category category = Category.builder()
                .id(categoryId)
                .name("전자제품")
                .description("전자제품 카테고리")
                .build();

        CategoryDto expectedDto = new CategoryDto(
                categoryId,
                "전자제품",
                "전자제품 카테고리",
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Mock 동작 설정
        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(category));
        given(mapper.toDto(category)).willReturn(expectedDto);

        // When: 카테고리 조회 메서드를 호출하면
        CategoryDto result = categoryService.getCategory(categoryId);

        // Then: 카테고리 정보가 정상적으로 반환된다
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(categoryId);
        assertThat(result.getName()).isEqualTo("전자제품");

        verify(categoryRepository).findById(categoryId);
        verify(mapper).toDto(category);
    }

    @Test
    @DisplayName("카테고리 조회 실패 - 카테고리 없음")
    void getCategory_CategoryNotFound_ThrowsException() {
        // Given: 존재하지 않는 카테고리 ID가 주어졌을 때
        Long categoryId = 999L;

        // Mock 동작 설정: 카테고리를 찾을 수 없음
        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        // When & Then: 카테고리 조회 시도 시 CATEGORY_NOT_FOUND 예외가 발생한다
        assertThatThrownBy(() -> categoryService.getCategory(categoryId))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("exceptionCode", ServiceExceptionCode.CATEGORY_NOT_FOUND);

        verify(categoryRepository).findById(categoryId);
    }

    @Test
    @DisplayName("전체 카테고리 조회 성공")
    void getAllCategories_Success() {
        // Given: 여러 카테고리가 존재할 때
        Category category1 = Category.builder()
                .id(1L)
                .name("전자제품")
                .build();

        Category category2 = Category.builder()
                .id(2L)
                .name("의류")
                .build();

        CategoryDto dto1 = new CategoryDto(
                1L, "전자제품", "전자제품 카테고리",
                null, null,
                LocalDateTime.now(), LocalDateTime.now()
        );

        CategoryDto dto2 = new CategoryDto(
                2L, "의류", "의류 카테고리",
                null, null,
                LocalDateTime.now(), LocalDateTime.now()
        );

        // Mock 동작 설정
        given(categoryRepository.findAll()).willReturn(Arrays.asList(category1, category2));
        given(mapper.toDto(category1)).willReturn(dto1);
        given(mapper.toDto(category2)).willReturn(dto2);

        // When: 전체 카테고리 조회 메서드를 호출하면
        List<CategoryDto> result = categoryService.getAllCategories();

        // Then: 모든 카테고리가 정상적으로 반환된다
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("전자제품");
        assertThat(result.get(1).getName()).isEqualTo("의류");

        verify(categoryRepository).findAll();
    }
}
