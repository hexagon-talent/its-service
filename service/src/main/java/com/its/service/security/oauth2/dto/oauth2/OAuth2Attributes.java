package com.its.service.security.oauth2.dto.oauth2;

import com.its.service.security.util.SocialType;
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
//            case  APPLE -> ofApple(attributes);
        };
    }

    private static OAuth2Attributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
        return OAuth2Attributes.builder().
                email(String.valueOf(kakaoAccount.get("email")))
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
                .name(attributes.get("nickname").toString())
                .profileImage(attributes.get("profile_image_url").toString())
                .build();
    }


}