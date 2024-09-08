package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChoiceErrorCode implements ErrorCode{
    CHOICE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHOICE-001", "해당 선지는 존재하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
