package com.its.service.domain.auth.security.oauth2.service;

import com.its.service.common.error.code.AuthErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.auth.security.oauth2.apple.AppleJwtUtil;
import com.its.service.domain.auth.security.oauth2.dto.oauth2.OAuth2Attributes;
import com.its.service.domain.user.entity.User;
import com.its.service.domain.user.entity.User.UserRole;

import com.its.service.domain.auth.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.domain.auth.security.oauth2.dto.OAuth2UserDTO;
import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.repository.UserRepository;
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
    private final AppleJwtUtil appleJwtUtil;

    /* OAuth2UserRequest  : 리소스 서버에서 제공되는 유저 정보 */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // Oauth2 서비스명
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 소셜 로그인 종류
        SocialType socialType = SocialType.from(registrationId);

        if (socialType == SocialType.APPLE) return handleAppleLogin(userRequest);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 인증된 사용자 정보
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // 소셜 로그인 attributes
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(socialType, attributes);

        log.info("loadUser : {}", oAuth2User);
        log.info("registrationId : {}", registrationId);


        return getCustomOAuth2User(oAuth2Attributes);
    }

    private OAuth2User getCustomOAuth2User(OAuth2Attributes oAuth2Attributes) {
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

    private OAuth2User handleAppleLogin(OAuth2UserRequest userRequest) {
        // Apple ID 토큰 추출
        String idToken = userRequest.getAdditionalParameters().get("id_token").toString();

        if (idToken == null || idToken.isEmpty()) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_FORMAT);
        }

        // JWT 디코딩하여 사용자 정보 추출
        Map<String, Object> jwtClaims = appleJwtUtil.decodeJwtTokenPayload(idToken);

        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(SocialType.APPLE, jwtClaims);

        return getCustomOAuth2User(oAuth2Attributes);
    }
}
