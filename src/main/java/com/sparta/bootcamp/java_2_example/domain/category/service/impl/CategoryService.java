package com.sparta.bootcamp.java_2_example.domain.category.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestCreateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestUpdateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.response.ResponseCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.search.SearchCategory;
import com.sparta.bootcamp.java_2_example.domain.category.service.CategoryCommandService;
import com.sparta.bootcamp.java_2_example.domain.category.service.CategoryQueryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.service.impl
 * @since : 2025. 11. 2.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements CategoryQueryService, CategoryCommandService {


	@Override
	public ResponseCategory createCategory(RequestCreateCategory requestCreate) {
		return null;
	}

	@Override
	public ResponseCategory updateCategory(Long id, RequestUpdateCategory requestUpdate) {
		return null;
	}

	@Override
	public Page<ResponseCategory> getCategories(SearchCategory search) {
		return null;
	}
}
