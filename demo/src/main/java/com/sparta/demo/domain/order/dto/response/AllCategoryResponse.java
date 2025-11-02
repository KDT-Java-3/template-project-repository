package com.sparta.demo.domain.order.dto.response;

import com.sparta.demo.domain.order.entity.Category;
import com.sparta.demo.domain.order.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AllCategoryResponse {
    private List<CategoryListItem> categoryList;

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class CategoryListItem {
        private Long id;
        /** 카테고리 이름 */
        private String name;
        /** 카테고리 설명 */
        private String description;
        /** 연관된 상품 리스트 */
        private List<Product> products;

        public static CategoryListItem buildCategoryFromEntity(Category category) {
            return CategoryListItem.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .products(category.getProducts())
                    .build();
        }
    }

    public static AllCategoryResponse buildFromEntity(List<Category> category) {
        List<CategoryListItem> list = category.stream()
                .map(CategoryListItem::buildCategoryFromEntity)
                .toList();
        return AllCategoryResponse.builder()
                .categoryList(list)
                .build();
    }
}
