package com.sparta.templateprojectrepository.controller;

import com.sparta.templateprojectrepository.dto.request.ProductCreateRequestDto;
import com.sparta.templateprojectrepository.dto.request.ProductFindRequestDto;
import com.sparta.templateprojectrepository.dto.response.ProductResponseDto;
import com.sparta.templateprojectrepository.entity.Product;
import com.sparta.templateprojectrepository.service.ProductSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductSerivce productService;

    //상품등록
    @PostMapping("/post")
    public void createProduct(@RequestBody ProductCreateRequestDto productRequestDto){
        productService.createProduct(productRequestDto);
    }

    //상품조회
    @GetMapping("/get")
    public ProductResponseDto getProductById(@RequestBody ProductFindRequestDto productRequestDto){
        Product product = productService.getProduct(productRequestDto);
        return ProductResponseDto.from(product);
    }

    //상품수정
    @PutMapping("/modify")
    public void modifyProduct(@RequestBody ProductCreateRequestDto productRequestDto) {
        productService.modifyProduct(productRequestDto);

    }


}
