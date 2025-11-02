package com.sparta.bootcamp.java_2_example.domain.product.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.sparta.bootcamp.java_2_example.domain.category.entity.Category;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestCreateProduct;
import com.sparta.bootcamp.java_2_example.domain.product.dto.request.RequestUpdateProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(nullable = false)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer stock;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	LocalDateTime updatedAt;


	public static Product of(Category category, RequestCreateProduct requestCreate) {
		return Product.builder()
			.category(category)
			.name(requestCreate.getProductName())
			.description(requestCreate.getDescription())
			.price(requestCreate.getPrice())
			.stock(requestCreate.getStock())
			.build();
	}

	public void update(Category category, RequestUpdateProduct product) {
		this.category = category;
		this.name = product.getProductName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.stock = product.getStock();
	}

	public void validateQuantity(Integer quantity) {
		if ((this.stock - quantity) < 0) {
			throw new IllegalArgumentException("Quantity must be greater than or equal to zero.");
		}
	}

	public void minusStock(Integer quantity) {
		this.stock -= quantity;
	}

}

