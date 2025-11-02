package com.sparta.bootcamp.java_2_example.domain.category.service.impl;

import org.springframework.stereotype.Service;

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

}
