package com.sparta.demo.domain.category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // 정적 팩토리 메서드
    public static Category create(String name, String description) {
        return Category.builder()
                .name(name)
                .description(description)
                .build();
    }

    public static Category createWithParent(String name, String description, Category parent) {
        return Category.builder()
                .name(name)
                .description(description)
                .parent(parent)
                .build();
    }

    // 업데이트 메서드
    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateParent(Category parent) {
        this.parent = parent;
    }

    public boolean isTopLevel() {
        return this.parent == null;
    }

    /**
     * 현재 카테고리부터 최상위 부모까지의 경로를 리스트로 반환
     * 예: 노트북 → [노트북, 컴퓨터, 전자제품]
     */
    public List<Category> getCategoryPath() {
        List<Category> path = new ArrayList<>();
        Category current = this;

        while (current != null) {
            path.add(current);
            current = current.getParent();
        }

        return path;
    }

    /**
     * 카테고리 경로를 문자열로 반환
     * 예: "전자제품 > 컴퓨터 > 노트북"
     * @param separator 구분자 (기본값: " > ")
     */
    public String getCategoryPathString(String separator) {
        List<Category> path = getCategoryPath();
        List<String> names = new ArrayList<>();

        // 역순으로 추가 (최상위 부모부터)
        for (int i = path.size() - 1; i >= 0; i--) {
            names.add(path.get(i).getName());
        }

        return String.join(separator, names);
    }

    /**
     * 카테고리 경로를 " > "로 구분하여 문자열로 반환
     */
    public String getCategoryPathString() {
        return getCategoryPathString(" > ");
    }

    /**
     * 카테고리의 깊이(depth)를 반환
     * 최상위 카테고리는 0, 그 하위는 1, 2, ...
     */
    public int getDepth() {
        int depth = 0;
        Category current = this.parent;

        while (current != null) {
            depth++;
            current = current.getParent();
        }

        return depth;
    }
}
