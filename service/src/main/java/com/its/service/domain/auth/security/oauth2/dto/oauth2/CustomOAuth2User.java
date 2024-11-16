package com.its.service.domain.auth.security.oauth2.dto.oauth2;

import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.entity.User.UserRole;
import com.its.service.domain.auth.security.oauth2.dto.OAuth2UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final OAuth2UserDTO oAuth2UserDTO;

    public CustomOAuth2User(OAuth2UserDTO oAuth2UserDTO) {
        this.oAuth2UserDTO = oAuth2UserDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "name", oAuth2UserDTO.getName(),
                "email", oAuth2UserDTO.getEmail(),
                "profileImage", oAuth2UserDTO.getProfileImage()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> oAuth2UserDTO.getRole().toString());
        return collection;
    }

    @Override
    public String getName() {
        return oAuth2UserDTO.getName();
    }
    public SocialType getRegistrationType() {return oAuth2UserDTO.getRegistrationType();}
    public String getEmail() {return oAuth2UserDTO.getEmail();}
    public Long getUserId() {return oAuth2UserDTO.getUserId();}
    public String getProfileImage() {return oAuth2UserDTO.getProfileImage();}
    public UserRole getRole() {return oAuth2UserDTO.getRole();}
}
