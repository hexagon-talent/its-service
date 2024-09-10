package com.its.service.domain.subject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateSubjectRequest(
    @Schema(description = "과목명") @NotNull(message = "과목명 입력해주세요")
    String subjectName,

    @Schema(description = "과목의 차시",example = "54") @NotNull(message = "과목의 차시를 입력해주세요")
    Integer round,

    @Schema(description = "시험 일정 (UTC, 날짜만)", example = "2024-09-09") @NotNull(message = "시험 일정을 입력해주세요")
    LocalDate examDate
) {
}
