package com.its.service.domain.classification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "대과목 응답 DTO")
@Builder
public record MajorResponse(
        @Schema(description = "대과목 ID") Long majorId,
        @Schema(description = "대과목 이름") String majorName,
        @Schema(description = "과목 ID") Long subjectId
) {}
