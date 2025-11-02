package com.sparta.bootcamp.java_2_example.domain.product.dto.response;

import static java.util.Objects.*;

import com.sparta.bootcamp.java_2_example.domain.category.entity.Category;

import lombok.Builder;
import lombok.Getter;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.dto.request
 * @since : 2025. 11. 2.
 */
@Builder
@Getter
public class ResponseProduct {

	private Long id;

	private Long parentId;

	private String name;

	public static ResponseProduct of(Category category) {
		Category parent = category.getParent();
		return ResponseProduct.builder()
					.id(category.getId())
					.name(category.getName())
					.parentId(isNull(parent) ? null : parent.getId())
					.build();
	}

}
