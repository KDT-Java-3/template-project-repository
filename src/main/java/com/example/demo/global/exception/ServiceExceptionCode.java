package com.example.demo.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ServiceExceptionCode {

    // Category
    NOT_FOUND_CATEGORY("카테고리를 찾을 수 없습니다."),
    CATEGORY_HAS_CHILDREN("하위 카테고리가 있어 삭제할 수 없습니다."),
    CATEGORY_HAS_PRODUCTS("연관된 상품이 있어 삭제할 수 없습니다."),

    // Product
    NOT_FOUND_PRODUCT("상품을 찾을 수 없습니다."),
    INSUFFICIENT_STOCK("상품의 재고가 부족합니다."),
    PRODUCT_HAS_COMPLETED_ORDERS("완료된 주문이 있어 상품을 삭제할 수 없습니다."),

    // Order
    NOT_FOUND_ORDER("주문을 찾을 수 없습니다."),
    INVALID_ORDER_STATUS("유효하지 않은 주문 상태입니다."),
    CANNOT_CANCEL_ORDER("취소할 수 없는 주문입니다."),
    EMPTY_ORDER_ITEMS("주문 항목이 비어있습니다."),
    INVALID_ORDER_QUANTITY("주문 수량이 유효하지 않습니다."),

    // Refund
    NOT_FOUND_REFUND("환불 요청을 찾을 수 없습니다."),
    INVALID_REFUND_STATUS("유효하지 않은 환불 상태입니다."),

    // User
    NOT_FOUND_USER("사용자를 찾을 수 없습니다.");

    final String message;
}
