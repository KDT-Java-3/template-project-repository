package com.example.demo.domain.product;

import com.example.demo.domain.category.Category;
import jakarta.persistence.*;
import lombok.*;

// DB Table DataType
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    // ===== Primary Key =====
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Foreign Keys (관계 매핑) =====
    // 외래키 매핑 (ManyToOne 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_products_category"))
    // Category 엔티티 참조 (JPA가 변환)
    private Category categoryId;

    // ===== 일반 필드 =====
    @Column( nullable = false, length = 200)
    private String name;

    // MySQL TEXT 타입 직접 지정 (VARCHAR보다 긴 텍스트 저장)
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // ===== JPA 라이프사이클 콜백 =====
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
