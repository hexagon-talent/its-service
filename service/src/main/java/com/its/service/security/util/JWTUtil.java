package com.its.service.security.util;


import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.common.error.exception.CustomException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {
    private final SecretKey secretKey;
    private static final String CLAIM_KEY_EMAIL = "email";
    private static final String CLAIM_KEY_ROLE = "role";
    private static final String CLAIM_KEY_CATEGORY = "category";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";


    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * 요청 헤더에서 JWT 액세스 토큰을 추출한다.
     * @param request HTTP 요청 객체
     * @return JWT 액세스 토큰 문자열
     */
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        log.info("[AUTH_INFO] 요청 헤더에 JWT 토큰이 존재하지 않음");
        throw new CustomException(AuthErrorCode.ACCESS_TOKEN_NOT_FOUND);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(CLAIM_KEY_ROLE, String.class);
    }

    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(CLAIM_KEY_CATEGORY, String.class);
    }
    public String getEmail(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(CLAIM_KEY_EMAIL, String.class);
    }
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createAccessJwt(String category, String email, String role, Long expiredMs) {
        return Jwts.builder()
                .claim(CLAIM_KEY_CATEGORY, category)
                .claim(CLAIM_KEY_EMAIL, email)
                .claim(CLAIM_KEY_ROLE, role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
    public String createRefreshJwt(String category, String email, Long expiredMs) {
        return Jwts.builder()
                .claim(CLAIM_KEY_CATEGORY, category)
                .claim(CLAIM_KEY_EMAIL, email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

}
