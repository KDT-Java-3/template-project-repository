package com.example.demo.domain.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody: JSON 응답을 자동으로 반환
@RequestMapping("/api/categories") // 이 컨트롤러의 모든 메서드는 /api/categories로 시작
@RequiredArgsConstructor // final 필드를 파라미터로 받는 생성자 자동 생성 (의존성 주입)
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {
    // ===== 의존성 주입 =====
    /**
     * CategoryService 의존성
     * - @RequiredArgsConstructor가 생성자 주입 자동 처리
     * public CategoryContoller(CategoryService categoryService) {
     *      this.categoryService = categoryService;
     * }
     */
    private final CategoryService categoryService;

    // ===== 1. 카테고리 등록 =====
    /** POST /api/categories - 새 카테고리 등록 */
    @PostMapping
    @Operation(summary = "Create a new category", description = "Creates a new category with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or category already exists")
    })
    public ResponseEntity<CategoryDto.Response> createCategory(
            // @Valid: DTO 검증
            // @RequestBody: JSON → 객체 변환
            @Valid @RequestBody CategoryDto.Request request
    ) {
        // Service 메서드 수행 (→ Repository → JPA 작업) 후, 응답 반환
        CategoryDto.Response response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // 201 Created 응답
    }

    // ===== 2. 카테고리 조회 =====

    // ===== 3. 카테고리 수정 =====
}
