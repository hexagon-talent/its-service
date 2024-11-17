package com.its.service.domain.auth.security.oauth2.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.service.common.error.code.AppleErrorCode;
import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.common.error.exception.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.its.service.common.error.code.AppleErrorCode.KEY_PATH_NOT_FOUND;


@Slf4j
@Component
@RequiredArgsConstructor
public class AppleJwtUtil {
    private final AppleProps appleProps;

    /**
     * 4.2 Apple OAuth2 클라이언트 시크릿 생성
     * @return 클라이언트 시크릿 (JWT)
     */
    public String createClientSecret() throws IOException {
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", appleProps.getKeyId());
        jwtHeader.put("alg", "ES256");


        if (appleProps.getKeyId() == null || appleProps.getTeamId() == null || appleProps.getClientId() == null) {
            log.error("[Flow-4.2.1] apple 구성 요소가 누락 되었습니다.");
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        }

        log.debug("[Flow-4.2.1] 클라이언트 시크릿 (JWT)생성을 시작합니다.");
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
     * 4.2.3 Apple에서 제공한 p8 파일로부터 Private Key를 가져오는 메소드
     */
    private PrivateKey getPrivateKey() throws IOException {
        String keyPath = appleProps.getKeyPath();
        File p8File = new File(keyPath);

        log.debug("[Flow-4.2.2] {} p8 파일로 부터 Private Key를 로드합니다.", keyPath);

        // p8 파일이 없거나 경로가 잘못되었을 때
        if (!p8File.exists()) {
            log.error("[Flow-4.2.3] Apple p8 키 파일을 찾을 수 없습니다: {}", keyPath);
            String formattedMessage = KEY_PATH_NOT_FOUND.getFormattedMessage(keyPath);
            throw new CustomException(KEY_PATH_NOT_FOUND, formattedMessage);
        }

        try (FileReader fileReader = new FileReader(p8File, StandardCharsets.UTF_8);
            PEMParser pemParser = new PEMParser(fileReader)){

            Object privateKeyInfo = pemParser.readObject();

            if (!(privateKeyInfo instanceof PrivateKeyInfo)) {
                log.error("[Flow-4.2.3] Apple p8 키 파일의 형식이 올바르지 않습니다: {}", keyPath);
                throw new CustomException(AuthErrorCode.INVALID_TOKEN_SIGNATURE);
            }

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKey privateKey = converter.getPrivateKey((PrivateKeyInfo) privateKeyInfo);// Private Key 반환
            log.debug("[Flow-4.2.3] Apple p8 키 파일을 성공적으로 로드했습니다: {}", keyPath);

            return privateKey;
        } catch (IOException e) {
            log.error("[Flow-4.2.3] Apple p8 키 파일을 읽는 도중 에러가 발생했습니다.");
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_SIGNATURE);
        }
    }

    /**
     * JWT 토큰의 Payload 부분을 디코딩하여 사용자 정보를 추출하는 메소드
     * @param jwtToken Apple에서 받은 id_token
     * @return Claims 객체 (JWT의 클레임 정보)
     */

    public Map<String, Object> decodeJwtTokenPayload(String jwtToken) {
        log.debug("[Flow-5.2] ID 토큰의 Payload 부분 디코딩을 시작합니다.");

        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            log.error("[Flow-5.2] 잘 못된 ID 토큰의 포멧입니다.");
            throw new CustomException(AppleErrorCode.INVALID_ID_TOKEN_FORMAT);
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map claimInfo = mapper.readValue(decodedString, Map.class);
            log.debug("[Flow-5.2] ID 토큰 디코딩을 성공 하였습니다.");
            return claimInfo;
        } catch (JsonProcessingException e) {
            log.error("[Flow-5.2] Apple JWT 디코딩 실패: {}", e.getMessage());
            throw new CustomException(AuthErrorCode.TOKEN_DECODING_FAILED);
        }
    }


}