package com.its.service.domain.auth.security.oauth2.apple;

import org.springframework.core.convert.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {
    private final OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    private final AppleJwtUtil appleJwtUtil;

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest request) {
        RequestEntity<?> entity = defaultConverter.convert(request);
        String registrationId = request.getClientRegistration().getRegistrationId();
        MultiValueMap<String, String> params = (MultiValueMap<String, String>) entity.getBody();
        // Apple OAuth2를 위한 client_secret 생성 및 추가
        if (registrationId.contains("apple")) {
            try {
                params.set("client_secret", appleJwtUtil.createClientSecret());
            } catch (IOException e) {
                throw new RuntimeException("Apple Client Secret 생성 오류", e);
            }
        }
        return new RequestEntity<>(params, entity.getHeaders(), entity.getMethod(), entity.getUrl());

    }
}
