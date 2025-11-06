package com.sparta.demo.service.dto.product;

import com.sparta.demo.controller.dto.product.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Service Layer에서 사용하는 상품 생성 DTO
 * Controller의 ProductRequest를 변환하여 전달
 */
@Getter
@AllArgsConstructor
public class ProductCreateDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;

    /**
     * ProductRequest를 ProductCreateDto로 변환하는 정적 팩토리 메서드
     */
    public static ProductCreateDto from(ProductRequest request) {
        return new ProductCreateDto(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getCategoryId()
        );
    }
}
