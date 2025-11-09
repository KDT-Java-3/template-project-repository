package com.example.week01_project.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 요청한 리소스를 찾을 수 없을 때 던지는 예외
 * 예: 상품, 카테고리, 주문 등 ID로 조회했는데 존재하지 않을 때
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String resourceName, Object id) {
        super(resourceName + " not found with id: " + id);
    }
}
