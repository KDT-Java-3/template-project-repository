package com.sprata.sparta_ecommerce.dto.param;

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
public class SearchProductDto {

    private static final long MIN_PRICE = 0;
    private static final long MAX_PRICE = 999_999_999_999L;

    @Builder.Default
    private Long categoryId = 0L;

    @Builder.Default
    private Long minPrice = MIN_PRICE;

    @Builder.Default
    private Long maxPrice = MAX_PRICE;

    @Builder.Default
    private String keyword = "";


    @Builder.Default
    private List<String> field = new ArrayList<>();

    @Builder.Default
    private List<String> direction = new ArrayList<>();

    // 다중 정렬 조건
    @Builder.Default
    private List<SortCondition> sortConditions = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SortCondition {
        private String field;
        private String direction;
    }

    public void makeSortCondition() {
        for (int i = 0; i < field.size(); i++) {
            String f = field.get(i);
            String d = direction.get(i) != null ? direction.get(i) : "desc";
            sortConditions.add(new SortCondition(f, d));
        }
    }
}
