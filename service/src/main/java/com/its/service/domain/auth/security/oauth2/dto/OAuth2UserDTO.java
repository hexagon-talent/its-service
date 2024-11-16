package com.its.service.domain.auth.security.oauth2.dto;

import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.entity.User;
import com.its.service.domain.user.entity.User.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2UserDTO {
    private final Long userId;
    private final String name;
    private final SocialType registrationType;
    private final String email;
    private final String profileImage;
    private final UserRole role;

    public static OAuth2UserDTO from(User user) {
        return OAuth2UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .registrationType(user.getRegistrationType())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .role(user.getUserRole())
                .build();
    }
}
