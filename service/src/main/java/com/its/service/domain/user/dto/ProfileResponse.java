package com.its.service.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProfileResponse(
        @Schema(description = "유저 고유 키 값", example ="1")
        Long userId,

        @Schema(description = "유저의 이름", example ="hi")
        String name,

        @Schema(description = "프로필 이미지의 URL", example ="http://example.com/image.jpg")
        String profileImage,

        @Schema(description = "유저의 이메일 주소", example ="example@example.com")
        String email
) {
}
