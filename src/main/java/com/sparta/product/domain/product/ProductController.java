package com.sparta.product.domain.product;

import com.sparta.product.domain.product.dto.request.RegisterRequest;
import com.sparta.product.domain.product.dto.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<RegisterResponse> registerProduct(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.register(request));

    }
}
