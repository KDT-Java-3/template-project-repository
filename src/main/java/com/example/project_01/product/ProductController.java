package com.example.project_01.product;

import com.example.project_01.product.dto.ProductGetRequest;
import com.example.project_01.product.dto.ProductCreateRequest;
import com.example.project_01.product.dto.ProductPatchRequest;
import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 번호를 이용한 상품 조회
     * @param productId 상품 번호
     * @return 해당 상품 번호에 해당하는 상품
     */
    @GetMapping(value = "/{productId}")
    public ResponseEntity<ProductGetRequest> getProducts(@PathVariable Integer productId) {
        if(productId == null) {
            log.error("getProducts: productId is empty");
            return null;
        }

        ProductGetRequest product = productService.findById(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductGetRequest> postProducts(@NonNull @RequestParam ProductCreateRequest productCreateRequest) {
        String name = productCreateRequest.getName();
        if(StringUtils.isEmpty(name)) {
            log.error("postProducts: Invalid postProduct, name is {}", name);
            throw new IllegalArgumentException("postProducts: Invalid postProduct, name is empty");
        }

        Integer price = productCreateRequest.getPrice();
        if(price == null
            || price <= 0) {
            log.error("postProducts: Invalid postProduct, price is {}", price);
            throw new IllegalArgumentException("Invalid postProduct, price must be greater than 0");
        }

        Integer stock = productCreateRequest.getStock();
        if(stock == null
            || stock < 0) {
            log.error("postProducts: Invalid postProduct, stock is {}", stock);
            throw new IllegalArgumentException("Invalid postProduct, stock must be greater than 0");
        }

        Integer categoryId = productCreateRequest.getCategoryId();
        if(categoryId == null) {
            log.error("postProducts: Invalid postProduct, category is null");
            ResponseEntity.badRequest();
            throw new IllegalArgumentException("Invalid postProduct, category must be selected");
        }

        return ResponseEntity.ok(productService.postProduct(productCreateRequest));
    }

    @PatchMapping
    public void patchProducts(@NonNull ProductPatchRequest request) {
        if(request.getName() == null) {
            log.error("patchProducts: Invalid patchProduct, name is null");
            throw new IllegalArgumentException("Invalid patchProduct, name must be selected");
        }

        if(request.getPrice() == null
            || request.getPrice() < 0) {
            log.error("patchProducts: Invalid patchProduct, price is null");
            throw new IllegalArgumentException("Invalid patchProduct, price must be selected");
        }

        if(request.getStock() == null
            || request.getStock() < 0) {
            log.error("patchProducts: Invalid patchProduct, stock is null");
            throw new IllegalArgumentException("Invalid patchProduct, stock must be selected");
        }

        if(request.getCategoryId() == null) {
            log.error("patchProducts: Invalid patchProduct, category is null");
            throw new IllegalArgumentException("Invalid patchProduct, category must be selected");
        }

        productService.patchProduct(request);

    }
}
