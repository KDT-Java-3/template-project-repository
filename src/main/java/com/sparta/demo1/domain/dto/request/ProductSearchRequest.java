package com.sparta.demo1.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchRequest {

  private String keyword;
  private Long minPrice;
  private Long maxPrice;
  private Long categoryId;
}