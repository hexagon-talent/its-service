package com.its.service.domain.auth.security.oauth2.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.common.error.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleJwtUtil {
    private final AppleProps appleProps;
    /**
     * Apple OAuth2 클라이언트 시크릿 생성
     * @return 클라이언트 시크릿 (JWT)
     */
    public String createClientSecret() throws IOException {
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", appleProps.getKeyId());
        jwtHeader.put("alg", "ES256");


        if (appleProps.getKeyId() == null || appleProps.getTeamId() == null || appleProps.getClientId() == null) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        }

        return Jwts.builder()
                .setHeaderParams(jwtHeader)
                .setIssuer(appleProps.getTeamId())  // Team ID
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5))) // 만료 시간 (5분)
                .setAudience(appleProps.getAppleUrl()) // Audience
                .setSubject(appleProps.getClientId())  // Client ID
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256) // Private Key로 서명
                .compact();
    }

    /**
     * Apple에서 제공한 p8 파일로부터 Private Key를 가져오는 메소드
     */
    private PrivateKey getPrivateKey() throws IOException {
        // p8 파일 경로를 ClassPathResource로 읽어오기
        ClassPathResource resource = new ClassPathResource(appleProps.getKeyPath());

        // p8 파일이 없거나 경로가 잘못되었을 때
        if (!resource.exists()) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        }
        // InputStream으로 파일 읽기
        InputStream in = resource.getInputStream();

        // InputStreamReader와 BufferedReader로 파일 내용을 읽기
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        PEMParser pemParser = new PEMParser(reader);

        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

        // PrivateKey 로딩이 실패한 경우
        if (object == null) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_SIGNATURE);
        }

        return converter.getPrivateKey(object); // Private Key 반환
    }

    /**
     * JWT 토큰의 Payload 부분을 디코딩하여 사용자 정보를 추출하는 메소드
     * @param jwtToken Apple에서 받은 id_token
     * @return Claims 객체 (JWT의 클레임 정보)
     */

    public Map<String, Object> decodeJwtTokenPayload(String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        }

        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(decodedString, Map.class);
        } catch (JsonProcessingException e) {
            log.error("Apple JWT 디코딩 실패: {}", e.getMessage());
            throw new CustomException(AuthErrorCode.TOKEN_DECODING_FAILED);
        }
    }



}