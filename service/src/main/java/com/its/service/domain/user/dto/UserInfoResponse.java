package com.its.service.domain.user.dto;

import com.its.service.domain.auth.security.util.SocialType;
import com.its.service.domain.user.entity.User.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "회원 프로필 응답 DTO")
public record UserInfoResponse(
        @Schema(description = "유저 고유 키 값", example ="1")
        Long userId,

        @Schema(description = "소셜 로그인 타입", example = "KAKAO")
        SocialType registrationType,

        @Schema(description = "유저의 이름", example ="hi")
        String name,

        @Schema(description = "프로필 이미지의 URL", example ="http://example.com/image.jpg")
        String profileImage,

        @Schema(description = "유저 상태", example = "SUBSCRIPTION")
        UserRole userRole,

        @Schema(description = "유저의 이메일 주소", example ="example@example.com")
        String email
) {
}
