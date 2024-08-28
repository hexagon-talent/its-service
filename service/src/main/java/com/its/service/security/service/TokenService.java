package com.its.service.security.service;

import com.its.service.common.error.exception.CustomException;
import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.domain.entity.User;
import com.its.service.domain.repository.UserRepository;
import com.its.service.security.util.JWTUtil;
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


    public String createAccessToken(String email, String role) {
        return jwtUtil.createAccessJwt(JWT_ACCESS_CATEGORY, email, role, accessTokenExpiration * 1000); // 밀리초 -> 초
    }

    public String createRefreshToken(String email) {
        return jwtUtil.createRefreshJwt(JWT_REFRESH_CATEGORY, email, refreshTokenExpiration * 1000); // 밀리초 -> 초
    }

    public User getUserByAccessToken(String accessToken) {
        String email = jwtUtil.getEmail(accessToken);
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("[AUTH_ERROR] 유효한 약관 동의 토큰에서 추출된 사용자 email: {}에 해당하는 사용자가 존재하지 않음", email);
            return new CustomException(AuthErrorCode.USER_NOT_EXIST);
        });
    }

}


