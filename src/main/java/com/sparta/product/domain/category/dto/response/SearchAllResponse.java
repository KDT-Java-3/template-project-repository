package com.sparta.product.domain.category.dto.response;

import com.sparta.product.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchAllResponse {
    private List<SearchResponse> searchResponseList;

    public static SearchAllResponse of(List<Category> categoryList) {

        List<SearchResponse> result = categoryList.stream().map(SearchResponse::of).toList();
        return new SearchAllResponse(result);
    }
}
