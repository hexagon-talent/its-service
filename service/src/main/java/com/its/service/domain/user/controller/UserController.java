package com.its.service.domain.user.controller;

import com.its.service.common.response.SuccessResponse;
import com.its.service.common.response.factory.ResponseFactory;
import com.its.service.domain.auth.security.oauth2.dto.oauth2.CustomOAuth2User;
import com.its.service.domain.user.dto.ProfileResponse;
import com.its.service.domain.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "사용자", description = "User API")
public class UserController {
    private final UserQueryService userQueryService;

    @GetMapping("/profile")
    @Operation(summary = "✅ [사용자]프로필 조회", description = "로그인한 유저의 프로필 조회 API")
    public ResponseEntity<SuccessResponse<ProfileResponse>> getMemberProfile(
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        ProfileResponse response = userQueryService.getUserProfile(user.getUserId());
        return ResponseFactory.success(response);
    }
}
