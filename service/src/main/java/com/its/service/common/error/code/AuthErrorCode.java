package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode{
    // 인증 및 권한 관련 에러
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-001", "사용자 인증에 실패했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH-002", "접근이 거부되었습니다. 이 리소스에 대한 권한이 없습니다."),
    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, "AUTH-003", "작업을 수행할 권한이 부족합니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH-004", "로그인에 실패했습니다."),

    // 토큰 관련 에러
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-005", "엑세스 토큰의 유효기간이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-006", "리프레시 토큰의 유효기간이 만료되었습니다."),
    TERMS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-007", "약관 동의 토큰의 유효기간이 만료되었습니다."),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST, "AUTH-008", "잘못된 토큰 형식입니다."),
    INVALID_TOKEN_SIGNATURE(HttpStatus.BAD_REQUEST, "AUTH-009", "토큰의 서명이 일치하지 않습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "AUTH-010", "토큰의 특정 헤더나 클레임이 지원되지 않습니다."),
    TOKEN_DECODING_FAILED(HttpStatus.BAD_REQUEST, "AUTH-011", "토큰 디코딩에 실패했습니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-012", "쿠키에 refreshToken 이 없습니다."),
    ACCESS_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-013", "요청 헤더에 accessToken 이 없습니다."),

    // 권한 애러
    USER_NOT_EXIST(HttpStatus.UNAUTHORIZED, "AUTH-014", "가입한 사용자가 존재하지 않습니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
