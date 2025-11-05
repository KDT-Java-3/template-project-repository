package com.sparta.product.domain.product.dto.request;

import com.sparta.product.domain.category.Category;
import com.sparta.product.domain.category.dto.response.SearchResponse;
import com.sparta.product.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterRequest {

    private String name;

    private Integer price;

    private Integer stock;

    private Long categoryId;

    public Product toEntity(SearchResponse searchResponse) {
        Category category = Category.builder()
                .id(searchResponse.getId())
                .name(searchResponse.getName())
                .description(searchResponse.getDescription())
                .build();
        return Product.builder()
                .name(this.getName())
                .stock(this.stock)
                .category(category)
                .price(this.price)
                .build();
    }
}
