package com.its.service.domain.classification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "소과목 응답 DTO")
@Builder
public record MinorResponse(
        @Schema(description = "소과목 ID") Long minorId,
        @Schema(description = "소과목 이름") String minorName,
        @Schema(description = "대과목 ID") Long majorId
) {}
