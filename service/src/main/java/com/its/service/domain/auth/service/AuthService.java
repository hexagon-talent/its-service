package com.its.service.domain.auth.service;

import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.auth.dto.response.RefreshResponse;
import com.its.service.domain.auth.security.util.JWTUtil;
import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.entity.User;

import com.its.service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final TokenService tokenService;
    private final UserRepository userRepository;


    public RefreshResponse reissueToken(String refreshToken) {
        log.info("[AUTH_INFO]refreshToken 을 이용해 accessToken 재발급: {}", refreshToken);
        // refresh 토큰 만료여부 검사
        if (jwtUtil.isExpired(refreshToken)) {
            log.info("refreshToken 만료: {}", refreshToken);
            throw new CustomException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        User user = tokenService.getUserByRefreshToken(refreshToken);
        String email = user.getEmail();
        String role = user.getUserRole().toString();
        SocialType registrationType = user.getRegistrationType();

        String newAccessToken = tokenService.createAccessToken(registrationType, email, role);
        return RefreshResponse.builder().accessToken(newAccessToken).build();
    }

}
