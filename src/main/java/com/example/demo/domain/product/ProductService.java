package com.example.demo.domain.product;

import com.example.demo.domain.category.Category;
import com.example.demo.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final 필드를 파라미터로 받는 생성자 자동 생성 (의존성 주입)
public class ProductService {
    // ===== 의존성 주입 =====
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // ============================================
    // -- 상품 등록
    // 비즈니스 로직:
    // 1. name 중복 체크
    // 2. Category 조회 (Long ID → Category 객체)
    // 3. DTO  → Entity 변환
    // 4. DB 저장
    // 5. Entity → DTO 변환 후 반환
    // ============================================
    public ProductDto.Response createProduct(ProductDto.Request request) {
        // 1. 비즈니스 규칙 검증: name 중복 체크
        // 커스텀 쿼리 메서드 (Query Method)
        // - existsByName() : name 존재 여부 확인
        if (productRepository.existsByName(request.getName())){
            throw new IllegalArgumentException("Product name already exists");
        }

        // 2. Category 조회 (Long ID → Category 객체)
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // 3. DTO → Entity 변환 (빌더 패턴 사용)
        Product product = Product.builder()
                .categoryId(category)  // Category 객체 설정
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        // 4. DB 저장
        // 기본 제공 메서드 (JpaRepository로부터 자동 상속)
        // - save() : INSERT 또는 UPDATE
        Product savedProduct = productRepository.save(product);

        // 5. Entity → DTO 변환 후 반환
        return ProductDto.Response.from(savedProduct);
    }

}
