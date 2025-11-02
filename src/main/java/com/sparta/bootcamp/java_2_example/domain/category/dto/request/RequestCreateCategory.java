package com.sparta.bootcamp.java_2_example.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : leeyounggyo
 * @package : com.sparta.bootcamp.java_2_example.domain.category.dto.request
 * @since : 2025. 11. 2.
 */
@Getter
@AllArgsConstructor
public class RequestCreateCategory {

	private Long parentId;

	@NotBlank
	private String name;

}
