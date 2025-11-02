package com.sparta.bootcamp.java_2_example.domain.category.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestCreateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.request.RequestUpdateCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.response.ResponseCategory;
import com.sparta.bootcamp.java_2_example.domain.category.dto.search.SearchCategory;
import com.sparta.bootcamp.java_2_example.domain.category.service.CategoryCommandService;
import com.sparta.bootcamp.java_2_example.domain.category.service.CategoryQueryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.controller
 * @since : 2025. 11. 2.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

	private final CategoryQueryService categoryQueryService;
	private final CategoryCommandService categoryCommandService;

	@PostMapping
	public ResponseEntity<ResponseCategory> createCategory(

		@Valid
		@RequestBody
		RequestCreateCategory requestCreate

	) {

		return ResponseEntity.ok(categoryCommandService.createCategory(requestCreate));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ResponseCategory> updateCategory(

		@PathVariable
		Long id,

		@Valid
		@RequestBody
		RequestUpdateCategory requestUpdate

	) {

		return ResponseEntity.ok(categoryCommandService.updateCategory(id, requestUpdate));
	}

	@GetMapping
	public ResponseEntity<Page<ResponseCategory>> getCategories(

		@ModelAttribute
		SearchCategory search

	) {

		return ResponseEntity.ok(categoryQueryService.getCategories(search));
	}

}
