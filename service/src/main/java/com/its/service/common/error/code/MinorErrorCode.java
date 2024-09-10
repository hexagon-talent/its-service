package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MinorErrorCode implements ErrorCode{
    MINOR_NOT_FOUND(HttpStatus.NOT_FOUND, "MINOR-001", "해당 소과목은 존재하지 않습니다."),
    MINOR_ALREADY_EXISTS(HttpStatus.CONFLICT, "MINOR-001", "해당 소과목은 존재하지 않습니다."),
    ;

    private HttpStatus httpStatus;
    private String code;
    private String message;
}
