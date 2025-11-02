package bootcamp.project.domain.purchase.dto;

import bootcamp.project.domain.purchase.entity.Purchase;
import bootcamp.project.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseProductDto {

    private Purchase purchase;
    private Product product;
    private Integer quantity;
    private BigDecimal price;

}
