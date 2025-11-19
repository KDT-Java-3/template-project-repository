package io.depark.commerceservice.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceException extends RuntimeException {
    String code;
    String message;

    public ServiceException(ServiceExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.name();
        this.message = super.getMessage();
    }

    public ServiceException(ServiceExceptionCode exceptionCode, String message) {
        super(exceptionCode.getMessage() + " : " + message);
        this.code = exceptionCode.name();
        this.message = message;
    }
}
