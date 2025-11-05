package com.jaehyuk.week_01_project.domain.category.controller;

import com.jaehyuk.week_01_project.config.auth.LoginUser;
import com.jaehyuk.week_01_project.domain.category.dto.CategoryResponse;
import com.jaehyuk.week_01_project.domain.category.dto.CreateCategoryRequest;
import com.jaehyuk.week_01_project.domain.category.dto.UpdateCategoryRequests;
import com.jaehyuk.week_01_project.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@Tag(name = "Category", description = "카테고리 관리 API")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = "카테고리 생성",
            description = """
                    새로운 카테고리를 생성합니다. (로그인 필요)

                    - parentId가 없으면 최상위 카테고리로 생성됩니다
                    - parentId가 있으면 해당 카테고리의 하위 카테고리로 생성됩니다
                    """
    )
    @PostMapping("/v1")
    public ResponseEntity<Long> createCategory(@LoginUser Long userId, @Valid @RequestBody CreateCategoryRequest request) {
        log.debug("카테고리 생성 요청 - userId: {}, name: {}, parentId: {}", userId, request.name(), request.parentId());

        Long categoryId = categoryService.createCategory(request);

        return ResponseEntity.ok(categoryId);
    }

    @Operation(
            summary = "카테고리 조회",
            description = """
                    모든 카테고리를 계층 구조로 조회합니다. (로그인 필요)

                    - 최상위 카테고리들이 반환되며, 각 카테고리는 children 필드에 하위 카테고리를 포함합니다
                    - 계층 구조를 통해 부모-자식 관계를 확인할 수 있습니다
                    """
    )
    @GetMapping("/v1")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@LoginUser Long userId) {
        log.debug("카테고리 조회 요청 - userId: {}", userId);

        List<CategoryResponse> categories = categoryService.getAllCategoriesHierarchy();

        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "카테고리 수정",
            description = """
                    카테고리를 수정합니다. (로그인 필요)
                    """
    )
    @PutMapping("/v1/{categoryId}")
    public ResponseEntity<Long> updateCategory(@LoginUser Long userId, @PathVariable Long categoryId, @Valid @RequestBody UpdateCategoryRequests request) {
        log.debug("카테고리 수정 요청 - userId: {}, name: {}, description: {}", userId, request.name(), request.description());

        Long updatedCategoryId = categoryService.updateCategory(categoryId, request);

        return ResponseEntity.ok(updatedCategoryId);
    }
}
