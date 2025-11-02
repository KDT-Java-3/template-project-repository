package com.sparta.templateprojectrepository.dto.request;

import com.sparta.templateprojectrepository.entity.Product;
import com.sparta.templateprojectrepository.entity.PurchaseProduct;
import com.sparta.templateprojectrepository.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class PurchaseCreateRequestDto {
    Long user_id;
    List<PurchaseProduct> purchasedProductList;
    BigDecimal totalprice;
    String status;

}
