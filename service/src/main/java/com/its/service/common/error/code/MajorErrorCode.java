package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MajorErrorCode implements ErrorCode{
    MAJOR_NOT_FOUND(HttpStatus.NOT_FOUND, "MAJOR-001", "해당 대과목은 존재하지 않습니다.")
    ;

    private HttpStatus httpStatus;
    private String code;
    private String message;
}
