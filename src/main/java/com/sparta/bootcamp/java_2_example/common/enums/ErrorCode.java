package com.sparta.bootcamp.java_2_example.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 공통 시스템 오류
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류"),
    DUPLICATE_RESOURCE(HttpStatus.BAD_REQUEST, "이미 존재하는 리소스입니다."),

    // 비즈니스 오류 (400 Bad Request)
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 도메인 예외
    REFUND_ALREADY_REQUESTED(HttpStatus.BAD_REQUEST, "이미 환불 요청이 존재합니다."),
    INVALID_REFUND_STATUS(HttpStatus.BAD_REQUEST, "주문취소 상태에서는 환불이 불가합니다.");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}