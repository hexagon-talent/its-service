package com.its.service.domain.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "해설 생성 요청 DTO")
public record CreateExplanationRequest(
    @Schema(description = "해설 내용 텍스트") String text,
    @Schema(description = "해설 이미지 URL",example = "[\"http://example.com/image1.jpg\",\"http://example.com/image2.jpg\"]") List<String> imageUrl
) {}
