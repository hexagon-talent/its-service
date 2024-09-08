package com.its.service.domain.classification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "과목 응답 DTO")
@Builder
public record SubjectResponse(
        @Schema(description = "과목 ID") Long subjectId,
        @Schema(description = "과목 이름") String subjectName,
        @Schema(description = "차시 정보") Integer round
) {}
