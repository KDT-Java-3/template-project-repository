package com.sparta.product.domain.product.dto.response;

import com.sparta.product.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {

    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String category;

    public static RegisterResponse of(Product product) {
        return RegisterResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory().getName())
                .build();
    }

}
