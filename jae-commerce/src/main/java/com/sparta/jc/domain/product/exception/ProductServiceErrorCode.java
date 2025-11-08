package com.sparta.jc.domain.product.exception;

import com.sparta.jc.global.exception.ServiceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 상품 서비스 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ProductServiceErrorCode implements ServiceErrorCode {

    PRODUCT_NOT_FOUND("productNotFound", HttpStatus.NOT_FOUND, "요청한 상품을 찾을 수 없습니다."),
    PRODUCT_NAME_DUPLICATION("productNameDuplication", HttpStatus.CONFLICT, "이미 존재하는 상품 이름입니다.");

    private final String code;
    private final HttpStatus status;
    private final String defaultMessage;

}
