package com.example.demo.domain.category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// @Transactional
public class CategoryService {

    // ===== 의존성 주입 =====
    private final CategoryRepository categoryRepository;

    // ============================================
    // -- 카테고리 등록
    // 비즈니스 로직:
    // 1. name 중복 체크
    // 2. Parent Category 조회 (Long ID → Category 객체)
    // 3. DTO  → Entity 변환
    // 4. DB 저장
    // 5. Entity → DTO 변환 후 반환
    // ============================================
    public CategoryDto.Response createCategory(CategoryDto.Request request) {

        // 1. 비즈니스 규칙 검증: username 중복 체크
        // 커스텀 쿼리 메서드 (Query Method)
        // - existsByName() : name 존재 여부 확인
        if (categoryRepository.existsByName(request.getName())){
            throw new IllegalArgumentException("Username already exists");
        }

        // 2. Parent Category 조회 (parentId가 있을 경우만)
        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
        }

        // 2. DTO → Entity 변환 (빌더 패턴 사용)
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .parentId(parent)
                .build();

        // 3. DB 저장
        // 기본 제공 메서드 (JpaRepository로부터 자동 상속)
        // - save() : INSERT 또는 UPDATE
        Category savedCatetory = categoryRepository.save(category);

        // 4. Entity → DTO 변환 후 반환
        return CategoryDto.Response.from(savedCatetory);
    }

}
