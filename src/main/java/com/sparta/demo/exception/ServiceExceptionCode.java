package com.sparta.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 비즈니스 예외 코드 및 메시지 정의
 * 모든 예외 상황을 한 곳에서 관리
 */
@Getter
@RequiredArgsConstructor
public enum ServiceExceptionCode {

    // Product 관련 예외
    PRODUCT_NOT_FOUND("PRODUCT_001", "상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INSUFFICIENT_STOCK("PRODUCT_002", "재고가 부족합니다.", HttpStatus.BAD_REQUEST),

    // Category 관련 예외
    CATEGORY_NOT_FOUND("CATEGORY_001", "카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PARENT_CATEGORY_NOT_FOUND("CATEGORY_002", "부모 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // User 관련 예외
    USER_NOT_FOUND("USER_001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_EMAIL("USER_002", "이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_USERNAME("USER_003", "이미 사용 중인 사용자명입니다.", HttpStatus.BAD_REQUEST),

    // Order 관련 예외
    ORDER_NOT_FOUND("ORDER_001", "주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_CANCEL_ORDER("ORDER_002", "주문을 취소할 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_STATUS("ORDER_003", "잘못된 주문 상태입니다.", HttpStatus.BAD_REQUEST),

    // Refund 관련 예외
    REFUND_NOT_FOUND("REFUND_001", "환불 요청을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_REFUND_STATE("REFUND_002", "환불 상태를 변경할 수 없습니다.", HttpStatus.BAD_REQUEST),

    // Validation 관련 예외
    INVALID_INPUT_VALUE("COMMON_001", "입력값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_PARAMETER("COMMON_002", "필수 파라미터가 누락되었습니다.", HttpStatus.BAD_REQUEST),

    // 일반 예외
    INTERNAL_SERVER_ERROR("COMMON_999", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
