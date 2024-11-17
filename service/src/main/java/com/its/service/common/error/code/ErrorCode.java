package com.its.service.common.error.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();

    default String getFormattedMessage(Object... args) {
        return String.format(getMessage(), args);
    }
}
