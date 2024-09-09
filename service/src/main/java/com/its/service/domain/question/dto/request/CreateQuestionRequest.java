package com.its.service.domain.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;


@Builder
public record CreateQuestionRequest(

    @Schema(description = "문제 번호", example = "11")
    String questionNumber,

    @Schema(description = "문제 제목", example = "다음 사례에서 관세법상 관세부과를 위한 과세물건의 확정시기에 관한 설명 으로 올바른 것은?")
    @NotNull(message = "문제 제목을 입력해주세요")
    String title,

    @Schema(description = "문제 내용 텍스트")
    String content,

    @Schema(description = "문제 내용 이미지 URL" , example = "[\"http://example.com/image1.jpg\",\"http://example.com/image2.jpg\"]")
    List<String> imageUrl,

    @Schema(description = "소과목 ID", example = "1")
    @NotNull(message = "minorId를 입력해주세요")
    Long minorId,

    @Schema(description = "과목 ID", example = "1")
    @NotNull(message = "subjectId를 입력해주세요")
    Long subjectId,

    @Schema(description = "선택지 목록")
    @NotNull
    List<CreateChoiceRequest> choices,

    @Schema(description = "해설")
    @NotNull
    CreateExplanationRequest explanation
    )

{}
