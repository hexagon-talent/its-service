package com.its.service.domain.classification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateMajorRequest(
        @Schema(description = "대과목명", example = "무역결제") @NotNull(message = "대과목명을 입력해주세요.")
        String majorName
        ) { }
