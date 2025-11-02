package com.sparta.bootcamp.java_2_example.domain.category.service.impl;

import static java.util.Objects.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestCreateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestUpdateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.response.ResponseCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.search.SearchCategory;
import com.sparta.bootcamp.java_2_example.domain.category.entity.Category;
import com.sparta.bootcamp.java_2_example.domain.category.repository.CategoryRepository;
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

	private final CategoryRepository categoryRepository;

	@Override
	public ResponseCategory createCategory(RequestCreateCategory requestCreate) {

		Category parentCategory = null;
		if (nonNull(requestCreate.getParentId())) {
			parentCategory = categoryRepository.findById(requestCreate.getParentId()).orElse(null);
		}

		Category savedCategory = categoryRepository.save(Category.of(requestCreate, parentCategory));

		return ResponseCategory.of(savedCategory);
	}

	@Override
	public ResponseCategory updateCategory(Long id, RequestUpdateCategory requestUpdate) {
		Optional<Category> byId = categoryRepository.findById(id);
		if (byId.isEmpty()) {
			throw new IllegalArgumentException("Category with id " + id + " does not exist");
		}

		Category category = byId.get();
		category.update(requestUpdate);

		return ResponseCategory.of(category);
	}

	@Override
	public List<ResponseCategory> getCategories(SearchCategory search) {
		 return categoryRepository.findAll().stream().map(ResponseCategory::of).toList();
	}

}
