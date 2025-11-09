package com.sparta.demo1.domain.category.controller;

import com.sparta.demo1.common.model.ApiResponseModel;
import com.sparta.demo1.domain.category.dto.request.CategoryReqDto;
import com.sparta.demo1.domain.category.dto.response.CategoryResDto;
import com.sparta.demo1.domain.category.service.CategoryService;
import com.sparta.demo1.domain.user.dto.request.UserReqDto;
import com.sparta.demo1.domain.user.dto.response.UserResDto;
import com.sparta.demo1.domain.user.entity.UserEntity;
import com.sparta.demo1.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ApiResponseModel<List<CategoryResDto.CategoryInfo>> getCategoryAll() {
        return new ApiResponseModel<>(categoryService.getAllCategory());
    }

    @GetMapping("/top-10-sale")
    public ApiResponseModel<List<CategoryResDto.CategorySimpleInfo>> getCategoryTop10Sale() {
        return new ApiResponseModel<>(categoryService.getCategoryTop10Sale());
    }

    @PostMapping("/register")
    public ApiResponseModel<Long> register(@Valid @RequestBody CategoryReqDto.CategoryRegisterDto categoryRegisterDto) {
        return new ApiResponseModel<>(categoryService.registerCategory(categoryRegisterDto.getName(), categoryRegisterDto.getDescription(), categoryRegisterDto.getParentId()));
    }

    @PatchMapping("/update")
    public ApiResponseModel<Boolean> updateUser(@Valid @RequestBody CategoryReqDto.CategoryUpdateDto categoryUpdateDto) {
        categoryService.updateCategory(categoryUpdateDto.getId(), categoryUpdateDto.getName(), categoryUpdateDto.getDescription(), categoryUpdateDto.getParentId());
        return new ApiResponseModel<>(true);
    }

    @DeleteMapping("/{id}")
    public ApiResponseModel<Boolean> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ApiResponseModel<>(true);
    }
}
