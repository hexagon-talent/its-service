package com.its.service.domain.subject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "과목 목록 응답 DTO")
@Builder
public record SubjectResponses(
        @Schema(description = "과목 목록") List<SubjectResponse> subjects
) {}
