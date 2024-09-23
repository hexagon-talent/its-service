package com.its.service.domain.auth.security.oauth2.apple;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppleProps {
    @Value("${apple.kid}") private String keyId;
    @Value("${apple.tid}") private String teamId;
    @Value("${apple.cid}") private String clientId;
    @Value("${apple.url}") private String appleUrl;
    @Value("${apple.path}") private String keyPath;
}
