package bootcamp.project.domain.product.controller;

import bootcamp.project.domain.product.dto.CreateProductDto;
import bootcamp.project.domain.product.dto.DeleteProductDto;
import bootcamp.project.domain.product.dto.ProductResponseDto;
import bootcamp.project.domain.product.dto.SearchProductDto;
import bootcamp.project.domain.product.dto.UpdateProductDto;
import bootcamp.project.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public String createProduct(@RequestBody CreateProductDto createProductDto) {
        productService.createProduct(createProductDto);
        return "success";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestBody UpdateProductDto updateProductDto) {
        productService.updateProduct(updateProductDto);
        return "success";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestBody DeleteProductDto deleteProductDto) {
        productService.deleteProduct(deleteProductDto);
        return "success";
    }

    @GetMapping("/{productId}")
    public ProductResponseDto findProduct(@PathVariable Long productId) {
        return productService.findProduct(productId);
    }

    @GetMapping("/all")
    public List<ProductResponseDto> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/search")
    public List<ProductResponseDto> searchProducts(@ModelAttribute SearchProductDto searchDto) {
        return productService.searchProducts(searchDto);
    }
}
