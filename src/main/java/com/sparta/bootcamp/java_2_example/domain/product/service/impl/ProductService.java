package com.sparta.bootcamp.java_2_example.domain.product.service.impl;

import org.springframework.stereotype.Service;

import com.sparta.bootcamp.java_2_example.domain.product.service.ProductCommandService;
import com.sparta.bootcamp.java_2_example.domain.product.service.ProductQueryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.product.service.impl
 * @since : 2025. 11. 2.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductQueryService, ProductCommandService {

}
