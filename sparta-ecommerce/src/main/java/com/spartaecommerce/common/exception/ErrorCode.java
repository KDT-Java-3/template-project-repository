package com.spartaecommerce.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // common
    INVALID_REQUEST(400, "INVALID_REQUEST", "The bad request."),

    // jpa
    ENTITY_NOT_FOUND(404, "ENTITY_NOT_FOUND", "The requested entity does not exist."),
    ENTITY_ALREADY_EXISTS(409, "ENTITY_ALREADY_EXISTS", "The entity already exists."),

    // order
    ORDER_INVALID_STATE_TRANSITION(400, "ORDER_INVALID_STATE_TRANSITION", "The order status cannot be changed to the requested status.");

    private final int httpStatus;
    private final String code;
    private final String message;
    }
