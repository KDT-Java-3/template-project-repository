package com.pepponechoi.project.domain.product.controller;

import com.pepponechoi.project.domain.product.dto.request.ProductCreateRequest;
import com.pepponechoi.project.domain.product.dto.request.ProductDeleteRequest;
import com.pepponechoi.project.domain.product.dto.request.ProductReadRequest;
import com.pepponechoi.project.domain.product.dto.request.ProductUpdateRequest;
import com.pepponechoi.project.domain.product.dto.response.ProductResponse;
import com.pepponechoi.project.domain.product.service.ProductService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.create(request);
        return ResponseEntity.created(
            URI.create(String.format("/api/products/%d", response.id()))
        ).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> list(@ModelAttribute ProductReadRequest request) {
        List<ProductResponse> response = productService.list(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        ProductResponse response = productService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        Boolean response = productService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody ProductDeleteRequest request) {
        Boolean response = productService.delete(request.getId(), request.getUserId());
        return ResponseEntity.ok(response);
    }
}
