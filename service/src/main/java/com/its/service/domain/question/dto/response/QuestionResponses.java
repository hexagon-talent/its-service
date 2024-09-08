package com.its.service.domain.question.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;


@Builder
@Schema(description = "문제들 응답 DTO")
public record QuestionResponses(
        @Schema(description = "문제 목록") List<QuestionResponse> questions
) { }
