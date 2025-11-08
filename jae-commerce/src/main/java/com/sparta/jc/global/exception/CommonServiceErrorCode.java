package com.sparta.jc.global.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * service_exception_code: 예외 코드-서비스 예외에서 사용
 */
@Getter
@RequiredArgsConstructor
public enum CommonServiceErrorCode implements ServiceErrorCode {

    NOT_FOUND("notFound", HttpStatus.NOT_FOUND, "요청한 데이터를 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String defaultMessage;

}
