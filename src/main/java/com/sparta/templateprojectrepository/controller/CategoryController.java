package com.sparta.templateprojectrepository.controller;

import com.sparta.templateprojectrepository.dto.request.CategoryCreateRequestDto;
import com.sparta.templateprojectrepository.dto.request.CategoryFindRequestDto;
import com.sparta.templateprojectrepository.dto.request.ProductCreateRequestDto;
import com.sparta.templateprojectrepository.dto.request.ProductFindRequestDto;
import com.sparta.templateprojectrepository.dto.response.CategoryResponseDto;
import com.sparta.templateprojectrepository.dto.response.ProductResponseDto;
import com.sparta.templateprojectrepository.entity.Category;
import com.sparta.templateprojectrepository.entity.Product;
import com.sparta.templateprojectrepository.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //카테고리 등록
    @PostMapping("/post")
    public void createCategory(@RequestBody CategoryCreateRequestDto categoroyRequestDto){
        categoryService.createCategory(categoroyRequestDto);
    }
    //카테고리 조회
    @GetMapping("/get")
    public CategoryResponseDto getCategoryById(@RequestBody CategoryFindRequestDto categoryRequestDto){
        Category category = categoryService.getCategory(categoryRequestDto);

        return CategoryResponseDto.from(category);
    }

    //카테고리 전체조회
    @GetMapping("/getAll")
    public List<Category> getCategory(){
        return categoryService.getCategoryAll();
    }
    
    //카테고리 수정
    @PutMapping("/register")
    public void modifyCategory(@RequestBody CategoryCreateRequestDto categoroyRequestDto) {
        categoryService.modifyCategory(categoroyRequestDto);

    }

}
