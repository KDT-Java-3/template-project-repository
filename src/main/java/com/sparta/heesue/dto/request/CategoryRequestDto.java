package com.sparta.heesue.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequestDto {
    private String name;
    private String description;
    private Long parentId; // 상위 카테고리 지정 가능
}
