package com.sparta.product.domain.category;

import com.sparta.product.domain.category.dto.request.RegisterRequest;
import com.sparta.product.domain.category.dto.request.UpdateRequest;
import com.sparta.product.domain.category.dto.response.RegisterResponse;
import com.sparta.product.domain.category.dto.response.SearchAllResponse;
import com.sparta.product.domain.category.dto.response.SearchResponse;
import com.sparta.product.domain.category.dto.response.UpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 등록
    @PostMapping
    public ResponseEntity<RegisterResponse> registerCategory(@RequestBody RegisterRequest request) {
        RegisterResponse response = categoryService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 카테고리 전체 조회
    @GetMapping
    public ResponseEntity<SearchAllResponse> searchCategoryList() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.searchCategoryList());
    }

    // 카테고리 단건 조회
    @GetMapping("{id}")
    public ResponseEntity<SearchResponse> searchCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.searchCategoryById(id));
    }

    // 카테고리 수정
    @PutMapping("{id}")
    public ResponseEntity<UpdateResponse> updateCategory(@PathVariable Long id, @RequestBody UpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(id,request));
    }
}
