package com.its.service.domain.auth.service;

import com.its.service.common.error.exception.CustomException;
import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.entity.User;

import com.its.service.domain.auth.security.util.JWTUtil;
import com.its.service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Value("${spring.jwt.access.expiration}")
    private Long accessTokenExpiration;

    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    @Value("${spring.jwt.access.category}")
    private String JWT_ACCESS_CATEGORY;

    @Value("${spring.jwt.refresh.category}")
    private String JWT_REFRESH_CATEGORY;


    public String createAccessToken(SocialType registrationType, String email, String role) {
        return jwtUtil.createAccessJwt(JWT_ACCESS_CATEGORY, registrationType, email, role, accessTokenExpiration * 1000); // 밀리초 -> 초
    }

    public String createRefreshToken(SocialType registrationType, String email) {
        return jwtUtil.createRefreshJwt(JWT_REFRESH_CATEGORY, registrationType, email, refreshTokenExpiration * 1000); // 밀리초 -> 초
    }

    public User getUserFromAccessToken(String accessToken) {
        String email = jwtUtil.getEmail(accessToken);
        SocialType registrationType = SocialType.from(jwtUtil.getRegistrationType(accessToken));
        return userRepository.findByEmailAndRegistrationType(email,registrationType).orElseThrow(() -> {
            log.error("[AUTH_ERROR] 유효한 약관 동의 토큰에서 추출된 사용자 email: {}에 해당하는 사용자가 존재하지 않음", email);
            return new CustomException(AuthErrorCode.USER_NOT_EXIST);
        });
    }
    public User getUserByRefreshToken(String refreshToken) {
        String email = jwtUtil.getEmail(refreshToken);
        SocialType registrationType = SocialType.from(jwtUtil.getRegistrationType(refreshToken));
        return userRepository.findByEmailAndRegistrationType(email,registrationType).orElseThrow(() -> {
            log.error("[AUTH_ERROR] 유효한 약관 동의 토큰에서 추출된 사용자 email: {}에 해당하는 사용자가 존재하지 않음", email);
            return new CustomException(AuthErrorCode.USER_NOT_EXIST);
        });
    }

}


