package com.sparta.bootcamp.java_2_example.domain.category.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sparta.bootcamp.java_2_example.domain.category.service.CategoryCommandService;
import com.sparta.bootcamp.java_2_example.domain.category.service.CategoryQueryService;
import com.sparta.bootcamp.java_2_example.domain.category.service.impl.CategoryService;

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
public class CategoryController {

	private final CategoryQueryService categoryQueryService;
	private final CategoryCommandService categoryCommandService;

}
