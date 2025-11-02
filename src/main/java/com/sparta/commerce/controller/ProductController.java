package com.sparta.commerce.controller;

import com.sparta.commerce.facade.ProductFacade;
import com.sparta.commerce.domain.product.dto.ModifyProductDto;
import com.sparta.commerce.domain.product.dto.SaveProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductFacade  productFacade;

    @GetMapping("/{id}")
    public ResponseEntity<?> findProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productFacade.findProduct(id));
    }

    @PostMapping()
    public ResponseEntity<?> saveProduct(
            @RequestBody SaveProductDto dto
    ) {
        return ResponseEntity.ok(productFacade.saveProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyProduct(
            @RequestBody ModifyProductDto dto
    ) {
        return ResponseEntity.ok(productFacade.modifyProduct(dto));
    }
}
