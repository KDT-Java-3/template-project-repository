package com.study.shop.domain.controller;

import com.study.shop.domain.dto.ProductDto;
import com.study.shop.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductDto.Resp create( @RequestBody ProductDto.CreateProduct product) throws IllegalAccessException {
        return productService.create(product);
    }

    @GetMapping("/search")
    public List<ProductDto.Resp> search(@ModelAttribute ProductDto.SearchProduct cond) {
        return productService.search(cond);
    }

    @PatchMapping("/{id}")
    public ProductDto.Resp update(@PathVariable Long id, @RequestBody ProductDto.UpdateProduct product) throws IllegalAccessException {
        return productService.update(id, product);
    }

}
