package com.its.service.domain.classification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "소과목 목록 응답 DTO")
@Builder
public record MinorResponses(
        @Schema(description = "소과목 목록") List<MinorResponse> minors

) {}