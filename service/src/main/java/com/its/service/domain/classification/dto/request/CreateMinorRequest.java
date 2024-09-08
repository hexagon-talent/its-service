package com.its.service.domain.classification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateMinorRequest(
    @Schema(description = "소과목명", example = "대금결제") @NotNull(message = "소과목명을 입력해주세요.")
    String minorName,

    @Schema(description = "대과목 ID") @NotNull(message = "Major ID 를 입력해주세요.")
    Long majorId

) {
}
