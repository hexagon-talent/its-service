package com.its.service.domain.question.dto.response;

import com.its.service.domain.question.dto.request.CreateChoiceRequest;
import com.its.service.domain.question.entity.Explanation;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "문제 응답 DTO")
public record QuestionResponse(
        @Schema(description = "과목 ID") Long subjectId,
        @Schema(description = "과목 이름") String subjectName,
        @Schema(description = "차시 정보") Integer round,

        @Schema(description = "대과목 ID") Long majorId,
        @Schema(description = "대과목 이름") String majorName,

        @Schema(description = "소과목 ID") Long minorId,
        @Schema(description = "소과목 이름") String minorName,

        @Schema(description = "문제 ID") String questionId,
        @Schema(description = "문제 번호") String questionNumber,
        @Schema(description = "문제 제목") String title,
        @Schema(description = "문제 내용 텍스트") String content,
        @Schema(description = "문제 내용 이미지 URL 목록") List<String> imageUrl,


        @Schema(description = "선지 목록") List<ChoicesResponse.ChoiceInfo> choices,

        @Schema(description = "해설") ExplanationResponse.ExplanationInfo explanation
) {
}
