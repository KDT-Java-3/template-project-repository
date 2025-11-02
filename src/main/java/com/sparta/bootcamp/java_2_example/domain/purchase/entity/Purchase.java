package com.sparta.bootcamp.java_2_example.domain.purchase.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.bootcamp.java_2_example.common.enums.PurchaseStatus;
import com.sparta.bootcamp.java_2_example.domain.product.entity.Product;
import com.sparta.bootcamp.java_2_example.domain.purchase.dto.request.RequestPurchase;
import com.sparta.bootcamp.java_2_example.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	User user;

	@Column(nullable = false)
	BigDecimal totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	PurchaseStatus status;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	LocalDateTime updatedAt;

	public static Purchase of(RequestPurchase request, User user, Product product) {
		return Purchase.builder()
					.user(user)
					.totalPrice(product.getPrice().multiply(new BigDecimal(request.getQuantity())))
					.status(PurchaseStatus.PENDING)
					.build();
	}

	public void changeStatus(PurchaseStatus status) {
		this.status = status;
	}

}
