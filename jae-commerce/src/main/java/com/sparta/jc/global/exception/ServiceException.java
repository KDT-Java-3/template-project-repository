package com.sparta.jc.global.exception;

import lombok.Getter;

/**
 * 글로벌 서비스 예외
 */
@Getter
public class ServiceException extends RuntimeException {

    // 에러 코드 enum
    private final ServiceErrorCode errorCode;
    // 상세 메시지
    private final String detailMessage;

    /**
     * ServiceErrorCode의 기본 메시지를 사용하는 생성자
     *
     * @param errorCode: ServiceErrorCode
     */
    public ServiceException(ServiceErrorCode errorCode) {
        super(errorCode.getDefaultMessage()); // RuntimeException의 메시지로 기본 메시지 설정
        this.errorCode = errorCode;
        this.detailMessage = errorCode.getDefaultMessage();
    }

    /**
     * 동적/커스텀 메시지를 사용하는 생성자
     *
     * @param errorCode:     ServiceErrorCode
     * @param detailMessage: 보여주고자 하는 메시지
     */
    public ServiceException(ServiceErrorCode errorCode, String detailMessage) {
        super(detailMessage); // RuntimeException의 메시지로 동적 메시지 설정
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
