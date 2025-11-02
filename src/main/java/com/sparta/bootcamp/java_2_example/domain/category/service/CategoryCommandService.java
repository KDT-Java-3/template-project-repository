package com.sparta.bootcamp.java_2_example.domain.category.service;

import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestCreateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestUpdateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.response.ResponseCategory;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.service
 * @since : 2025. 11. 2.
 */
public interface CategoryCommandService {

	ResponseCategory createCategory(RequestCreateCategory requestCreate);

	ResponseCategory updateCategory(Long id, RequestUpdateCategory requestUpdate);

}
