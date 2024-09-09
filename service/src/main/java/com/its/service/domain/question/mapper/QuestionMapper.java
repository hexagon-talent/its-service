package com.its.service.domain.question.mapper;

import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.question.dto.request.CreateQuestionRequest;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.dto.response.QuestionResponses;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.subject.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ChoiceMapper.class, ExplanationMapper.class})
public interface QuestionMapper {

    @Mapping(target = "subjectId", source = "subject.subjectId")
    @Mapping(target = "subjectName", source = "subject.subjectName")
    @Mapping(target = "round", source = "subject.round")
    @Mapping(target = "majorId", source = "minor.major.majorId")
    @Mapping(target = "majorName", source = "minor.major.majorName")
    @Mapping(target = "minorId", source = "minor.minorId")
    @Mapping(target = "minorName", source = "minor.minorName")
    QuestionResponse toQuestionResponse(Question question , Minor minor , Subject subject); ;

    @Mapping(target = "questionId", ignore = true) // 생성 시 questionId 무시
    Question toEntity(CreateQuestionRequest request);

    // List<Question>을 QuestionResponses로 변환
    default QuestionResponses toQuestionResponses(List<Question> questions, Minor minor, Subject subject) {
        List<QuestionResponse> questionResponses = questions.stream()
                .map(question -> toQuestionResponse(question, minor, subject))
                .collect(Collectors.toList());
        return QuestionResponses.builder().questions(questionResponses).build();
    }
}
