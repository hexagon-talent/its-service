package com.its.service.security.oauth2.service;

import com.its.service.domain.entity.User;
import com.its.service.domain.entity.User.UserRole;
import com.its.service.domain.repository.UserRepository;
import com.its.service.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.security.oauth2.dto.OAuth2UserDTO;
import com.its.service.security.oauth2.dto.oauth2.OAuth2Attributes;
import com.its.service.security.util.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    /*
    * OAuth2UserRequest  : 리소스 서버에서 제공되는 유저 정보
    * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // Oauth2 서비스명
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 인증된 사용자 정보
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // 소셜 로그인 종류
        SocialType socialType = SocialType.from(registrationId);
        // 소셜 로그인 attributes
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, attributes);

        log.info("loadUser : {}", oAuth2User);
        log.info("registrationId : {}", registrationId);


        User user = userRepository.findByEmail(oAuth2Attributes.getEmail())
                .orElseGet(() -> {
                    User createdUser = User.builder()
                            .email(oAuth2Attributes.getEmail())
                            .name(oAuth2Attributes.getName())
                            .userRole(UserRole.USER)
                            .profileImage(oAuth2Attributes.getProfileImage())
                            .build();
                    log.info("회원 가입 user : {}", createdUser.getEmail());
                    return userRepository.save(createdUser);
                });

        log.info("로그인 user : {}", user.getEmail());

        OAuth2UserDTO oAuth2UserDTO = OAuth2UserDTO.from(user);
        return new CustomOAuth2User(oAuth2UserDTO);
    }
}
