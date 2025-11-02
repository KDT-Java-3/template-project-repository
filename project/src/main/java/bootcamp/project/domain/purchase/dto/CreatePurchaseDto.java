package bootcamp.project.domain.purchase.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseDto {

    private Long purchaseId;

    @NotNull(message = "사용자 ID를 입력하세요.")
    private Long userId;

    @NotNull(message = "상품 ID를 입력하세요.")
    private Long productId;

    @NotNull(message = "수량을 입력하세요.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer quantity;

    @NotBlank(message = "배송지를 입력하세요.")
    private String shippingAddress;

}
