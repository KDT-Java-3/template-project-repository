package com.sparta.project1.domain.product.api;

import com.sparta.project1.domain.PageResponse;
import com.sparta.project1.domain.product.api.dto.ProductRegisterRequest;
import com.sparta.project1.domain.product.api.dto.ProductResponse;
import com.sparta.project1.domain.product.service.ProductFindService;
import com.sparta.project1.domain.product.service.ProductModifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {
    private final ProductModifyService productModifyService;
    private final ProductFindService productFindService;


    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody ProductRegisterRequest request) {
        productModifyService.register(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        ProductResponse response = productFindService.getProduct(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<PageResponse<ProductResponse>> getListByKeyword(@RequestParam(value = "name", required = false) String name,
                                                                  @RequestParam(value = "minPrice", required = false) Long minPrice,
                                                                  @RequestParam(value = "maxPrice", required = false) Long maxPrice,
                                                                  @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                                  @RequestParam(value = "page") int page,
                                                                  @RequestParam(value = "size") int size
                                                                  ) {
        PageResponse<ProductResponse> productResponses = productFindService.getListByKeyword(name, minPrice, maxPrice, categoryId, page - 1, size);

        return ResponseEntity.ok(productResponses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable("id") Long id,
                                              @RequestBody ProductRegisterRequest request) {
        productModifyService.updateProduct(id, request);

        return ResponseEntity.ok().build();
    }
}
