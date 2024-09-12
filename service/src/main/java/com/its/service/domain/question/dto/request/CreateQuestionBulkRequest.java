package com.its.service.domain.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateQuestionBulkRequest(
        @Schema(description = "등록할 문제 목록")
        @NotNull(message = "문제 목록을 입력해주세요")
        @Valid List<CreateQuestionRequest> questions

) {}
