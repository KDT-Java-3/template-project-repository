package com.sparta.demo1.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryCreateRequest {

  @NotBlank(message = "카테고리 이름은 필수 입력입니다.")
  private String name;

  private String description;

  private Long parentId;  // 최상위 카테고리면 null, 하위 카테고리면 부모 ID
}
