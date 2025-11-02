package com.example.demo.domain.category;

import jakarta.persistence.*;
import lombok.*;

// DB Table DataType
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    // ===== Primary Key =====
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== 일반 필드 =====
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(nullable = false, length = 500)
    private String description;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ===== 자기 참조 관계 (계층 구조) =====
    /**
     * 부모 카테고리
     * - 최상위 카테고리는 parent가 null
     * - 예: "전자제품" (최상위) → "휴대폰" (하위)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentId;

    /**
     * 자식 카테고리 목록
     * - 양방향 관계: parent 필드와 매핑
     * - 계층 구조 탐색 가능
     */
    @OneToMany(mappedBy = "parentId")
    @Builder.Default
    private List<Category> children = new ArrayList<>();


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
