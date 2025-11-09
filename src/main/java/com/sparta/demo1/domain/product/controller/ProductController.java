package com.sparta.demo1.domain.product.controller;

import com.sparta.demo1.common.model.ApiResponseModel;
import com.sparta.demo1.domain.category.dto.request.CategoryReqDto;
import com.sparta.demo1.domain.category.dto.response.CategoryResDto;
import com.sparta.demo1.domain.category.service.CategoryService;
import com.sparta.demo1.domain.product.dto.request.ProductReqDto;
import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import com.sparta.demo1.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ApiResponseModel<ProductResDto.ProductInfo> getProductById(@PathVariable Long id) {
        return new ApiResponseModel<>(productService.getProductInfoDetail(id));
    }

//    @PostMapping("/filter")
//    public ApiResponseModel<List<ProductResDto.ProductInfo>> getFilterProductAll(@Valid @RequestBody ProductReqDto.ProductFilterDto productFilterDto) {
//        return new ApiResponseModel<>(productService.getProductInfoList(productFilterDto.getFilterCategoryIdList(), productFilterDto.getMinPrice(), productFilterDto.getMaxPrice(), productFilterDto.getNameKeyWord(),  productFilterDto.getStock()));
//    }

    @PostMapping("/filter")
    public ApiResponseModel<Page<ProductResDto.ProductInfo>> getFilterProductAll(
            @Valid @RequestBody ProductReqDto.ProductFilterDto productFilterDto,
            Pageable pageable
    ) {
        Page<ProductResDto.ProductInfo> result = productService.getProductInfoList(
                productFilterDto.getFilterCategoryIdList(),
                productFilterDto.getMinPrice(),
                productFilterDto.getMaxPrice(),
                productFilterDto.getNameKeyWord(),
                productFilterDto.getStockFilter(),
                pageable,
                productFilterDto.getProductOrderByList()
        );

        return new ApiResponseModel<>(result);
    }

    @PostMapping("/register")
    public ApiResponseModel<Long> register(@Valid @RequestBody ProductReqDto.ProductRegisterDto productRegisterDto) {
        return new ApiResponseModel<>(productService.registerProduct(productRegisterDto.getName(), productRegisterDto.getDescription(), productRegisterDto.getPrice(), productRegisterDto.getStock(), productRegisterDto.getCategoryId()));
    }

    @PatchMapping("/update")
    public ApiResponseModel<Boolean> updateUser(@Valid @RequestBody ProductReqDto.ProductUpdateDto productUpdateDto) {
        productService.updateProduct(productUpdateDto.getId(), productUpdateDto.getName(), productUpdateDto.getDescription(), productUpdateDto.getPrice(), productUpdateDto.getStock(), productUpdateDto.getCategoryId());
        return new ApiResponseModel<>(true);
    }

    @DeleteMapping("/{id}")
    public ApiResponseModel<Boolean> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ApiResponseModel<>(true);
    }
}
