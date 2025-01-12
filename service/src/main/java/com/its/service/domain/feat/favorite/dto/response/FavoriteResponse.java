package com.its.service.domain.feat.favorite.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "즐겨찾기 응답 DTO")
@Builder
public record FavoriteResponse(
        @Schema(description = "즐겨찾기 문제 식별자") String questionId
)
{}
