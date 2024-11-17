package com.its.service.domain.auth.security.oauth2.service;

import com.its.service.common.error.code.AppleErrorCode;
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


        return getCustomOAuth2User(oAuth2Attributes, socialType);
    }

    private OAuth2User getCustomOAuth2User(OAuth2Attributes oAuth2Attributes, SocialType socialType) {
        User user = userRepository.findByEmailAndRegistrationType(oAuth2Attributes.getEmail(), socialType)
                .orElseGet(() -> {
                    User createdUser = User.builder()
                            .email(oAuth2Attributes.getEmail())
                            .name(oAuth2Attributes.getName())
                            .profileImage(oAuth2Attributes.getProfileImage())
                            .userRole(UserRole.USER)
                            .registrationType(socialType)
                            .build();
                    log.info("회원 가입 user : [{}] : {}", createdUser.getRegistrationType() ,createdUser.getEmail());
                    return userRepository.save(createdUser);
                });

        log.info("로그인 user : [{}] : {}", user.getRegistrationType() ,user.getEmail());

        OAuth2UserDTO oAuth2UserDTO = OAuth2UserDTO.from(user);
        return new CustomOAuth2User(oAuth2UserDTO);
    }

    private OAuth2User handleAppleLogin(OAuth2UserRequest userRequest) {
        // Apple ID 토큰 추출
        Object token = userRequest.getAdditionalParameters().get("id_token");
        if (token == null) {
            log.error("[Flow-5.1] Apple 로그인 시 id_token이 제공되지 않았습니다.");
            throw new CustomException(AppleErrorCode.INVALID_ID_TOKEN);
        }

        String idToken = token.toString();
        if (idToken == null || idToken.isEmpty()) {
            log.error("[Flow-5.1] Apple 로그인 시 제공된 id_token이 비어 있습니다.");
            throw new CustomException(AppleErrorCode.INVALID_ID_TOKEN);
        }

        // JWT 디코딩하여 사용자 정보 추출
        Map<String, Object> jwtClaims = appleJwtUtil.decodeJwtTokenPayload(idToken);

        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(SocialType.APPLE, jwtClaims);
        log.debug("[Flow-5.3] ID 토큰으로 부터 추출한 정보를 바탕으로 Login 절차를 시작합니다.");
        return getCustomOAuth2User(oAuth2Attributes, SocialType.APPLE);
    }
}
