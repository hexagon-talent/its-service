package com.its.service.domain.question.mapper;

import com.its.service.domain.question.dto.request.CreateChoiceRequest;
import com.its.service.domain.question.dto.response.ChoicesResponse;
import com.its.service.domain.question.entity.Choice;
import com.its.service.domain.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {

    // 요청 DTO를 Choice 엔티티로 변환
    Choice toEntity(CreateChoiceRequest request);

    // Choice 엔티티를 ChoiceInfo DTO로 변환
    @Named("toChoiceInfo")
    @Mapping(target = "choiceNumber", source = "choice.choiceNumber")
    @Mapping(target = "text", source = "choice.text")
    @Mapping(target = "imageUrl", source = "choice.imageUrl")
    ChoicesResponse.ChoiceInfo toChoiceInfo(Choice choice);

    @Mapping(target = "questionId", source = "question.questionId")
    @Mapping(target = "choices", source = "question.choices", qualifiedByName = "toChoiceInfo")
    ChoicesResponse toChoicesResponse(Question question);
}
