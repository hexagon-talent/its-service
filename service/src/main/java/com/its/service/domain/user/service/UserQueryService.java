package com.its.service.domain.user.service;

import com.its.service.common.error.code.UserErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.auth.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.entity.User;
import com.its.service.domain.user.dto.UserInfoResponse;
import com.its.service.domain.user.mapper.UserMapper;
import com.its.service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserInfoResponse getUserProfile(CustomOAuth2User oAuth2User) {

        String email = oAuth2User.getEmail();
        SocialType registrationType = oAuth2User.getRegistrationType();

        User user = userRepository.findByEmailAndRegistrationType(email, registrationType).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return userMapper.toInfoResponse(user);
    }
}
