package bootcamp.project.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @NotNull(message = "카테고리를 선택하세요.")
    private Long categoryId;

    @NotBlank(message = "상품명을 입력하세요.")
    private String name;

    @NotBlank(message = "상품 설명을 입력하세요.")
    private String description;

    @NotNull(message = "가격을 입력하세요.")
    @Positive(message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    @NotNull(message = "재고를 입력하세요.")
    @Positive(message = "재고는 0보다 커야 합니다.")
    private Integer stock;

}
