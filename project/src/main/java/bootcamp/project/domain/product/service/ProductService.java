package bootcamp.project.domain.product.service;

import bootcamp.project.domain.category.entity.Category;
import bootcamp.project.domain.category.repository.CategoryRepository;
import bootcamp.project.domain.product.dto.CreateProductDto;
import bootcamp.project.domain.product.dto.DeleteProductDto;
import bootcamp.project.domain.product.dto.ProductResponseDto;
import bootcamp.project.domain.product.dto.SearchProductDto;
import bootcamp.project.domain.product.dto.UpdateProductDto;
import bootcamp.project.domain.product.entity.Product;
import bootcamp.project.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public boolean isProductExists(Long productId) {
        return productRepository.existsById(productId);
    }

    public boolean isCategoryExists(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    public void createProduct(CreateProductDto createProductDto) {
        if (!isCategoryExists(createProductDto.getCategoryId())) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }

        productRepository.save(Product.builder()
                .category(categoryRepository.findById(createProductDto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")))
                .name(createProductDto.getName())
                .stock(createProductDto.getStock())
                .price(createProductDto.getPrice())
                .description(createProductDto.getDescription())
                .build());
    }

    public void updateProduct(UpdateProductDto updateProductDto) {
        //상품 존재 여부 체크
        Product product = productRepository.findById(updateProductDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        //카테고리 체크
        Category category = categoryRepository.findById(updateProductDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        //상품 정보 업데이트 (변경 감지로 자동 저장됨)
        product.update(category, updateProductDto);
    }

    public void deleteProduct(DeleteProductDto deleteProductDto) {
        if (!isProductExists(deleteProductDto.getProductId())) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        productRepository.deleteById(deleteProductDto.getProductId());
    }

    public ProductResponseDto findProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return ProductResponseDto.from(product);
    }

    public List<ProductResponseDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        
        return products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> searchProducts(SearchProductDto searchDto) {
        List<Product> products = productRepository.findProductsByCriteria(
                searchDto.getCategoryId(),
                searchDto.getKeyword(),
                searchDto.getMinPrice(),
                searchDto.getMaxPrice()
        );
        
        return products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }


}
