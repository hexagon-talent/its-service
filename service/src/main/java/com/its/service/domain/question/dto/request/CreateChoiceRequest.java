package com.its.service.domain.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "선지 생성 요청 DTO")
public record CreateChoiceRequest(
        @Schema(description = "선지 번호", example = "1")
        @NotNull(message = "선지 번호을 입력해주세요")
        String choiceNumber,

        @Schema(description = "선지 텍스트", example = "이서연 , 안정진, 황병훈")
        String text,

        @Schema(description = "이미지 URL 목록", example = "[\"http://example.com/image1.jpg\",\"http://example.com/image2.jpg\"]")
        List<String> imageUrl

) { }
