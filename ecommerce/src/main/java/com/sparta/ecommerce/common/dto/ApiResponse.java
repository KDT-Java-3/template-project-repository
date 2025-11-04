package com.sparta.ecommerce.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    Boolean result;
    Error error;
    T message;

}
