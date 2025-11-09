package com.sparta.demo1.domain.refund.dto.mapper;

import com.sparta.demo1.domain.purchase.entity.PurchaseEntity;
import com.sparta.demo1.domain.refund.dto.response.RefundResDto;
import com.sparta.demo1.domain.refund.entity.RefundEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RefundMapper {

    RefundMapper INSTANCE = Mappers.getMapper(RefundMapper.class);

    /**
     * Ex)
     * @Mapping(source = "productName", target = "name") // productName -> name 이름이 다른 필드 매핑
     * @Mapping(source = "user.username", target = "username")  // 중첩 객체의 필드를 평탄화하여 매핑
     **/

    /**
     * ProductEntity -> ProductResDto.ProductInfo
     */
    RefundResDto.RefundInfo toRes(RefundEntity refundEntity);
}
