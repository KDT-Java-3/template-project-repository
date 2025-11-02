package com.sparta.bootcamp.java_2_example.domain.category.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sparta.bootcamp.java_2_example.domain.category.dto.response.ResponseCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.search.SearchCategory;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.service
 * @since : 2025. 11. 2.
 */
public interface CategoryQueryService {

	List<ResponseCategory> getCategories(SearchCategory search);

}
