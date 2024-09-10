package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExplanationErrorCode implements ErrorCode{
    EXPLANATION_NOT_FOUND(HttpStatus.NOT_FOUND, "EXPLANATION-001", "해당 해설은 존재하지 않습니다."),
    EXPLANATION_ACCESS_DENIED(HttpStatus.FORBIDDEN, "EXPLANATION-002", "해당 해설에 접근 권한이 없습니다."),
    EXPLANATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "EXPLANATION-003", "해당 해설에 해설은 이미 존재합니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
