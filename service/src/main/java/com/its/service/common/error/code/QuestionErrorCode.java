package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QuestionErrorCode implements ErrorCode{
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION-001", "해당 문제는 존재하지 않습니다."),
    QUESTION_ACCESS_DENIED(HttpStatus.FORBIDDEN, "QUESTION-002", "해당 문제에 접근 권한이 없습니다."),
    QUESTION_ALREADY_EXISTS(HttpStatus.CONFLICT, "QUESTION-003", "해당 문제는 이미 존재합니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
