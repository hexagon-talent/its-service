package com.its.service.domain.auth.security.oauth2.dto;

import com.its.service.domain.user.entity.User;
import com.its.service.domain.user.entity.User.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2UserDTO {
    private final Long userId;
    private final String name;
    private final String email;
    private final String profileImage;
    private final UserRole role;

    @Builder
    public OAuth2UserDTO(Long userId, String name, String email, String profileImage, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
    }

    public static OAuth2UserDTO from(User user) {
        return OAuth2UserDTO.builder()
                .userId(user.getUser_id())
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .role(user.getUserRole())
                .build();
    }
}
