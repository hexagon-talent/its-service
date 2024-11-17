package com.its.service.domain.auth.security.oauth2.apple;

import com.its.service.common.error.code.AppleErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.auth.security.util.SocialType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {
    private final OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    private final AppleJwtUtil appleJwtUtil;
    private static final String APPLE_REGISTRATION_ID = SocialType.APPLE.getRegistrationId();

    /**
     * 4. Spring Security 내부의 OAuth2 클라이언트에 의해 자동으로 호출
     *
     */

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest request) {
        RequestEntity<?> entity = defaultConverter.convert(request);

        String registrationId = request.getClientRegistration().getRegistrationId();
        if (!registrationId.equalsIgnoreCase(APPLE_REGISTRATION_ID)) {
            return entity;
        }

        MultiValueMap<String, String> params = extractParams(entity);

        // Apple OAuth2를 위한 client_secret 생성 및 추가
        try {
            String clientSecret = appleJwtUtil.createClientSecret();
            params.set("client_secret", clientSecret);
            log.debug("[Flow-4.2.3] Apple client_secret을 성공적으로 추가했습니다.");
        } catch (IOException e) {
            log.error("[Flow-4.2.3] ID 토큰 요청에 실패했습니다. {}", e.getMessage());
            throw new CustomException(AppleErrorCode.ID_TOKEN_REQUEST_FAILED);
        }

        log.debug("[Flow-4.2.3] ID 토큰 요청에 성공 했습니다.");
        return new RequestEntity<>(params, entity.getHeaders(), entity.getMethod(), entity.getUrl());
    }

    /**
     * RequestEntity에서 MultiValueMap<String, String> 타입의 파라미터를 안전하게 추출합니다.
     *
     * @param entity RequestEntity 객체
     * @return MultiValueMap<String, String> 타입의 파라미터 또는 null
     */
    @SuppressWarnings("unchecked")
    private MultiValueMap<String, String> extractParams(RequestEntity<?> entity) {
        Object body = entity.getBody();
        if (body instanceof MultiValueMap) {
            return (MultiValueMap<String, String>) body;
        }
        return null;
    }
}
