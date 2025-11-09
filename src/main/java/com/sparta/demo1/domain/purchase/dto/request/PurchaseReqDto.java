package com.sparta.demo1.domain.purchase.dto.request;

import com.sparta.demo1.domain.product.enums.ProductOrderBy;
import com.sparta.demo1.domain.product.enums.ProductStockFilter;
import com.sparta.demo1.domain.purchase.enums.PurchaseOrderBy;
import com.sparta.demo1.domain.purchase.enums.PurchaseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseReqDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PurchaseFilterDto {
        private Long userId;
        private PurchaseStatus purchaseStatus;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private ProductStockFilter stockFilter;
        private List<PurchaseOrderBy> orderByList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PurchaseCreateDto {
        @NotBlank(message = "유저 ID는 필수입니다.")
        private Long userId;

        @NotBlank(message = "주소는 필수입니다.")
        private String shippingAddress;

        private List<PurchaseCreateProductInfo> purchaseCreateProductInfoList;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class PurchaseCreateProductInfo {
        @NotBlank(message = "제품 ID는 필수입니다.")
        private Long productId;

        @Positive(message = "수량은 양수여야합니다.")
        @NotBlank(message = "수량을 입력해주세요.")
        private Integer quantity;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductUpdateDto {
        @NotBlank(message = "제품 ID 필수값을 입력해주세요.")
        private Long id;

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 2, max = 10, message = "이름은 2자 이상 10자리 이하여야 합니다.")
        private String name;

        @NotBlank(message = "제품 설명을 입력해주세요.")
        private String description;

        @PositiveOrZero(message = "가격은 0이거나 0보다 커야합니다.")
        @NotBlank(message = "가격을 입력해주세요.")
        private BigDecimal price;

        @PositiveOrZero(message = "제품 수는 0이거나 0보다 커야합니다.")
        @NotBlank(message = "제품 수를 입력해주세요.")
        private Integer stock;

        @NotBlank(message = "관련 카테고리를 입력해주세요.")
        private Long categoryId;
    }
}
