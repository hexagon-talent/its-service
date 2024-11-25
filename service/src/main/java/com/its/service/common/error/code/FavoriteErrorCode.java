package com.its.service.common.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode implements ErrorCode{
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND, "FAVORITE-001", "해당 즐겨찾기는 존재하지 않습니다."),
    FAVORITE_ALREADY_EXISTS(HttpStatus.CONFLICT, "FAVORITE-002", "해당 즐겨찾기는 이미 존재 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
