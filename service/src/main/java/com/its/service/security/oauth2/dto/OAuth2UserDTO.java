package com.its.service.security.oauth2.dto;

import com.its.service.domain.entity.User;
import com.its.service.domain.entity.User.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2UserDTO {
    private final Long userId;
    private final String name;
    private final String email;
    private final String profileImage;
    private final Boolean allowance;
    private final UserRole role;

    @Builder
    public OAuth2UserDTO(Long userId, String name, String email, String profileImage, Boolean allowance, UserRole role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.allowance = allowance;
        this.role = role;
    }

    public static OAuth2UserDTO from(User user) {
        return OAuth2UserDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .allowance(user.getAllowance())
                .role(user.getUserRole())
                .build();
    }
}
