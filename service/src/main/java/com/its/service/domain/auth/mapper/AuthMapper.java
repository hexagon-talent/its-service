package com.its.service.domain.auth.mapper;

import com.its.service.domain.auth.dto.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "tokenType", constant = "bearer")  // JWT 토큰 타입을 상수로 지정
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    LoginResponse toLoginResponse(String accessToken, String refreshToken);
}
