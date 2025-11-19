package io.depark.commerceservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceExceptionCode {
    NOT_FOUND_PRODUCT("상품을 찾을 수 없습니다"),
    INSUFFICIENT_STOCK("상품의 재고가 부족합니다."),
    NOT_FOUND_USER("사용자를 찾을 수 없습니다"),
    NOT_FOUND_PURCHASE("구매 정보를 찾을 수 없습니다"),
    NOT_ALLOWED_REFUND("환불 가능한 상태가 아닙니다"),
    NOT_FOUND_PARENT_CATEGORY("부모 카테고리를 찾을 수 없습니다"),
    NOT_FOUND_CATEGORY("카테고리를 찾을 수 없습니다"),
    NOT_ALLOWED_DELETE_PRODUCT("상품 삭제 가능한 상태가 아닙니다."),
    NOT_ALLOWED_DELETE_CATEGORY("카테고리 삭제 가능한 상태가 아닙니다."),
    NOT_PURCHASE_OWNER("주문을 환불할 권한이 없습니다. (소유자 불일치)"),
    NOT_FOUND_REFUND("환불 정보를 찾을 수 없습니다."),
    INVALID_REFUND_STATUS_CODE("유효하지 않은 환불 상태 코드입니다.");

    final String message;
}
