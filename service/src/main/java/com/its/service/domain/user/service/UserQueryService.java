package com.its.service.domain.user.service;

import com.its.service.common.error.code.UserErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.user.entity.User;
import com.its.service.domain.user.dto.ProfileResponse;
import com.its.service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;

    public ProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return ProfileResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .email(user.getEmail())
                .build();
    }
}
