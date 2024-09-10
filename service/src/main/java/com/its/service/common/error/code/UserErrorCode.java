package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode  {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-001", "해당 사용자는 존재하지 않습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER-002", "해당 사용자는 이미 존재 합니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
