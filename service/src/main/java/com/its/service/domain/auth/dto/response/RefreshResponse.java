package com.its.service.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "토큰 응답")
public record RefreshResponse (@Schema(description = "엑세스 토큰", example = "...") String accessToken) {
}