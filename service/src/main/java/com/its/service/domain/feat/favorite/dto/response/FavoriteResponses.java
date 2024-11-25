package com.its.service.domain.feat.favorite.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "즐겨찾기 응답 DTO")
@Builder
public record FavoriteResponses(
        @Schema(description = "즐겨찾기 문제 목록") List<String> favoriteQuestionIds
)
{}
