package com.sparta.bootcamp.java_2_example.domain.product.service;

import java.util.List;

import com.sparta.bootcamp.java_2_example.domain.category.dto.search.SearchCategory;
import com.sparta.bootcamp.java_2_example.domain.product.dto.response.ResponseProduct;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.product.service
 * @since : 2025. 11. 2.
 */
public interface ProductQueryService {
	List<ResponseProduct> getProducts(SearchCategory search);

	ResponseProduct getProduct(Long id);
}
