package com.example.week01_project.web;


import com.example.week01_project.dto.product.ProductRequest;
import com.example.week01_project.dto.product.ProductResponse;
import com.example.week01_project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid ProductRequest req){
        return service.create(req); // 등록 후 ID 반환 (QueryDSL 기반 저장은 JPA 사용 + ID 반환 충족)
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id){ return service.get(id); }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid ProductRequest req){ service.update(id, req); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){ service.delete(id); }

    @GetMapping
    public Page<ProductResponse> search(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean includeZeroStock,
            Pageable pageable){
        return service.search(categoryId, minPrice, maxPrice, includeZeroStock, pageable);
    }
}

