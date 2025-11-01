package com.sparta.restful_1week.domain.product.controller;

import com.sparta.restful_1week.domain.product.dto.ProductInDTO;
import com.sparta.restful_1week.domain.product.dto.ProductOutDTO;
import com.sparta.restful_1week.domain.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @PostMapping("/products")
    public ProductOutDTO createProduct(@RequestBody ProductInDTO productInDTO) {
        ProductService productService = new ProductService();
        return productService.createProduct(productInDTO);
    }

    @GetMapping("/products")
    public List<ProductOutDTO> getProduct() {
        ProductService productService = new ProductService();
        return productService.getProduct();
    }

    @PutMapping("/products/{id}")
    public ProductOutDTO updateProduct(@PathVariable Long id, @RequestBody ProductInDTO productInDTO) {
        ProductService productService = new ProductService();
        return productService.updateProduct(id, productInDTO);
    }
}
