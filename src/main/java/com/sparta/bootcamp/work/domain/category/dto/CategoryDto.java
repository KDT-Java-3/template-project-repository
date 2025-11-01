package com.sparta.bootcamp.work.domain.category.dto;

import com.sparta.bootcamp.work.domain.category.entity.Category;
import com.sparta.bootcamp.work.domain.product.dto.ProductDto;
import com.sparta.bootcamp.work.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private String description;

    private List<ProductDto> products;

    public static CategoryDto fromCategory(Category category){
       return CategoryDto.builder()
               .id(category.getId())
               .name(category.getName())
               .description(category.getDescription())
               .build();
    }

    public static CategoryDto fromCategoryAndProducts(Category category, List<Product> products){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .products(ProductDto.fromProducts(products))
                .build();
    }

}
