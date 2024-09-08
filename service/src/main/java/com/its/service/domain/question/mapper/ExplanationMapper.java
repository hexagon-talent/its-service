package com.its.service.domain.question.mapper;

import com.its.service.domain.question.dto.request.CreateExplanationRequest;
import com.its.service.domain.question.dto.response.ExplanationResponse;
import com.its.service.domain.question.entity.Explanation;
import com.its.service.domain.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExplanationMapper {

    // 요청 DTO를 Explanation 엔티티로 변환
    Explanation toEntity(CreateExplanationRequest request);

    // Question에서 ExplanationResponse로 변환
    @Mapping(target = "questionId", source = "question.questionId")
    @Mapping(target = "explanation.text", source = "question.explanation.text")
    @Mapping(target = "explanation.imageUrl", source = "question.explanation.imageUrl")
    ExplanationResponse toExplanationResponse(Question question);
}
