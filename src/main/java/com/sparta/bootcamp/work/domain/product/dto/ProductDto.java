package com.sparta.bootcamp.work.domain.product.dto;

import com.sparta.bootcamp.work.domain.category.dto.CategoryDto;
import com.sparta.bootcamp.work.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

    private CategoryDto category;

    public static ProductDto fromProduct(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice().doubleValue())
                .stock(product.getStock())
                .description(product.getDescription())
                .category(CategoryDto.fromCategory(product.getCategory()))
                .build();
    }


    public static List<ProductDto> fromProducts(List<Product> products) {

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductDto.fromProduct(product));
        }

        return  productDtos;
    }

}
