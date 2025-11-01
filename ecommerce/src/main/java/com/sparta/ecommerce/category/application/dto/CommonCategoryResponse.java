package com.sparta.ecommerce.category.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonCategoryResponse { // 추상 클래스로 만들어 인스턴스 생성을 막음
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
