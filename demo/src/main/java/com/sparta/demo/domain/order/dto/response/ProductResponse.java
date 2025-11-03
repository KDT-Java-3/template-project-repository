package com.sparta.demo.domain.order.dto.response;

import com.sparta.demo.domain.order.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductResponse {
    /** 상품명*/
    private String name;
    /** 가격 */
    private BigDecimal price;
    /** 재고 */
    private Integer stock;
    /** 상품 설명 */
    private String description;
    /** 연관 카테고리 */
    private Long categoryId;

    public static ProductResponse buildFromEntity(Product product) {
        if(product == null) return null;

        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
