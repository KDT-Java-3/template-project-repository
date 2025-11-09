package com.sparta.jc.global.exception;

import org.springframework.http.HttpStatus;

/**
 * service_exception_code: 예외 코드-서비스 예외에서 사용
 */
public interface ServiceErrorCode {

    /**
     * 에러_코드
     */
    String getCode();

    /**
     * HTTP_STATUS
     */
    HttpStatus getStatus();

    /**
     * 기본_설정_메시지
     */
    String getDefaultMessage();

}
