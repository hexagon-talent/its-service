package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum AppleErrorCode implements ErrorCode{
    KEY_PATH_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLE-001", "Apple p8 키를 찾을 수 없습니다: %s"),
    CLIENT_SECRET_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "APPLE-002", "Apple Client Secret 생성에 실패했습니다."),
    INVALID_REGISTRATION_ID(HttpStatus.CONFLICT, "APPLE-003", "OAuth2 제공자가 Apple이 아닙니다.올바르지 않습니다."),
    ID_TOKEN_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "APPLE-004", "ID 토큰 요청에 실패했습니다."),
    INVALID_ID_TOKEN(HttpStatus.CONFLICT,"APPLE-005", "유효하지 않은 ID 토큰 입니다."),
    INVALID_ID_TOKEN_FORMAT(HttpStatus.CONFLICT,"APPLE-005", "잘못된 ID 토큰 입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
