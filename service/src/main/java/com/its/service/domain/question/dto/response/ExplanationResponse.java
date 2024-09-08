package com.its.service.domain.question.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record ExplanationResponse(
        @Schema(description = "문제 ID") String questionId,
        @Schema(description = "문제 해설") ExplanationInfo explanation

) {
    @Builder
    public record ExplanationInfo(
            @Schema(description = "해설 내용 텍스트") String text,
            @Schema(description = "해설 이미지 URL") List<String> imageUrl

    ){}
}
