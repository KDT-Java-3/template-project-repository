package com.example.demo.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceExceptionCode {
    // 서비스 로직에서 필요한 Exception들을 Enum
    NOT_FOUND_PRODUCT("상품을 찾을수 없습니다");

    final String message;
}
