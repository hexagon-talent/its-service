package com.its.service.domain.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record QueryMinorQuestionRequest(
        @Schema(description = "과목 ID", example = "1")
        @NotNull(message = "subjectId를 입력해주세요")
        Long subjectId,

        @Schema(description = "소과목 ID", example = "1")
        @NotNull(message = "minorId를 입력해주세요")
        Long minorId
) {
}
