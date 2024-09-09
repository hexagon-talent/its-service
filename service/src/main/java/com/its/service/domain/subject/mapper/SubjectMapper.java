package com.its.service.domain.subject.mapper;

import com.its.service.domain.subject.dto.response.SubjectResponse;
import com.its.service.domain.subject.dto.response.SubjectResponses;
import com.its.service.domain.subject.dto.request.CreateSubjectRequest;
import com.its.service.domain.subject.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    // Subject 변환 (Entity -> Response)
    @Mapping(target = "subjectId", source = "subject.subjectId")
    @Mapping(target = "subjectName", source = "subject.subjectName")
    @Mapping(target = "round", source = "subject.round")
    SubjectResponse toSubjectResponse(Subject subject);

    // Subject 생성 시 (DTO -> Entity)
    @Mapping(target = "subjectId", ignore = true)  // subjectId는 자동 생성
    Subject toSubjectEntity(CreateSubjectRequest request);

    default SubjectResponses toSubjectResponses(List<Subject> subjects) {
        List<SubjectResponse> subjectResponses = subjects.stream()
                .map(this::toSubjectResponse)
                .toList();
        return SubjectResponses.builder().subjects(subjectResponses).build();
    }
}
