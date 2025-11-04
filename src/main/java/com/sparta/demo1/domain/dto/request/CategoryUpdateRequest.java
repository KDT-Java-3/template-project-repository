package com.sparta.demo1.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryUpdateRequest {

  private String name;

  private String description;

  private Long parentId;  // 부모 카테고리 변경 가능
}