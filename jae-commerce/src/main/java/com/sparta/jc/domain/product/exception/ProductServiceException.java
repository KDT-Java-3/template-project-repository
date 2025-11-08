package com.sparta.jc.domain.product.exception;

import com.sparta.jc.global.exception.ServiceErrorCode;
import com.sparta.jc.global.exception.ServiceException;

/**
 *
 */
public class ProductServiceException extends ServiceException {

    public ProductServiceException(ServiceErrorCode errorCode) {
        super(errorCode);
    }

    public ProductServiceException(ServiceErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }

}
