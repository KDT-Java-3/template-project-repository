package com.sparta.demo1.domain.category.dto.response;

import com.sparta.demo1.domain.product.dto.response.ProductResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class CategoryResDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategoryInfo{
        private Long id;
        private Long parentId;
        private String name;
        private String description;
        private List<ProductResDto.ProductInfo> productInfoList;

        @Builder
        public CategoryInfo(Long id, Long parentId, String name, String description, List<ProductResDto.ProductInfo> productInfoList) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
            this.description = description;
            this.productInfoList = productInfoList;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CategorySimpleInfo{
        private Long id;
        private Long parentId;
        private String name;

        @Builder
        public CategorySimpleInfo(Long id, Long parentId, String name) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
        }
    }
}
