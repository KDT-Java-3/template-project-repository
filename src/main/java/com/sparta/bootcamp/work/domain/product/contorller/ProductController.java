package com.sparta.bootcamp.work.domain.product.contorller;

import com.sparta.bootcamp.work.domain.product.dto.ProductCreateRequest;
import com.sparta.bootcamp.work.domain.product.dto.ProductDto;
import com.sparta.bootcamp.work.domain.product.dto.ProductEditRequest;
import com.sparta.bootcamp.work.domain.product.dto.ProductSearchRequest;
import com.sparta.bootcamp.work.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/product")
    public ResponseEntity<ProductDto> getProductOne(@RequestBody ProductSearchRequest productSearchRequest) {
        return ResponseEntity.ok(productService.getProductOne(productSearchRequest));
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProduct() {
        return ResponseEntity.ok(productService.getProductAll());
    }

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        long id =  productService.createProduct(productCreateRequest);
        return ResponseEntity.ok("Product created with id: " + id);
    }


    @PutMapping("/product")
    public ResponseEntity<ProductDto> editProduct(@RequestBody ProductEditRequest productEditRequest) {
        return ResponseEntity.ok(productService.editProduct(productEditRequest));
    }




}
