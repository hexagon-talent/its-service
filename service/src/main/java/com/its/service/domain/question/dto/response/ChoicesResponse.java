package com.its.service.domain.question.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "특정 문제에 대한 선택지 목록 응답 DTO")
public record ChoicesResponse(
    @Schema(description = "문제 ID") String questionId,
    @Schema(description = "선지 목록") List<ChoiceInfo> choices
) {
    @Builder
    public record ChoiceInfo(
            @Schema(description = "선지 번호") String choiceNumber,
            @Schema(description = "선지 텍스트") String text,
            @Schema(description = "선지 이미지 URL 목록") List<String> imageUrl
    ){}
}
