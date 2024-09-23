package com.its.service.domain.auth.security.oauth2.dto.oauth2;

import com.its.service.domain.auth.security.oauth2.dto.OAuth2UserDTO;
import com.its.service.domain.auth.security.util.SocialType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Attributes {
    private final Map<String, Object> attributes;
    private final String email;
    private final String name;
    private final String profileImage;

    @Builder(access = AccessLevel.PRIVATE)
    private OAuth2Attributes(Map<String, Object> attributes, String email, String name, String profileImage) {
        this.attributes = attributes;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
    }

    public static OAuth2Attributes of(SocialType socialType, Map<String, Object> attributes) {
        return switch (socialType) {
            case  KAKAO -> ofKakao(attributes);
            case  NAVER -> ofNaver(attributes);
            case  GOOGLE -> ofGoogle(attributes);
            case  APPLE -> ofApple(attributes);
        };
    }

    private static OAuth2Attributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
        return OAuth2Attributes.builder()
                .email(kakaoAccount.get("email").toString())
                .name(profile.get("nickname"))
                .profileImage(profile.get("profile_image_url"))
                .build();
    }

    private static OAuth2Attributes ofNaver(Map<String, Object> attributes) {
        Map<String,String> response = (Map<String, String>) attributes.get("response");
        return OAuth2Attributes.builder()
                .email(response.get("email"))
                .name(response.get("name"))
                .profileImage(response.get("profile_image"))
                .build();
    }

    private static OAuth2Attributes ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .email(attributes.get("email").toString())
                .name(attributes.get("name").toString())
                .profileImage(attributes.get("picture").toString())
                .build();
    }

    private static OAuth2Attributes ofApple(Map<String, Object> attributes) {
        // Apple OAuth2에서는 초기 로그인 시 사용자 정보(email, name)를 한 번만 받을 수 있음
        // 이후 Apple은 사용자 고유 ID와 이메일 정보만 제공
        return OAuth2Attributes.builder()
                .email(attributes.get("email").toString())
                .name(attributes.containsKey("name") ? attributes.get("name").toString() : "Unknown user")
                .profileImage(null) // Apple은 프로필 이미지 정보를 제공하지 않음
                .build();
    }


}