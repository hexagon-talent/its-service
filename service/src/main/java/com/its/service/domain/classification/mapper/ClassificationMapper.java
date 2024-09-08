package com.its.service.domain.classification.mapper;

import com.its.service.domain.classification.dto.request.CreateMajorRequest;
import com.its.service.domain.classification.dto.request.CreateMinorRequest;
import com.its.service.domain.classification.dto.request.CreateSubjectRequest;
import com.its.service.domain.classification.dto.response.*;
import com.its.service.domain.classification.entity.Major;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.entity.Subject;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class, MajorMapper.class, MinorMapper.class})
public interface ClassificationMapper {
    // Subject 변환
    SubjectResponse toSubjectResponse(Subject subject);
    Subject toSubjectEntity(CreateSubjectRequest request);
    default SubjectResponses toSubjectResponses(List<Subject> subjects) {
        List<SubjectResponse> subjectResponses = subjects.stream()
                .map(this::toSubjectResponse)
                .collect(Collectors.toList());
        return SubjectResponses.builder().subjects(subjectResponses).build();
    }


    // Major 변환
    MajorResponse toMajorResponse(Major major);
    Major toMajorEntity(CreateMajorRequest request);
    default MajorResponses toMajorResponses(List<Major> majors) {
        List<MajorResponse> majorResponses = majors.stream()
                .map(this::toMajorResponse)
                .collect(Collectors.toList());
        return MajorResponses.builder().majors(majorResponses).build();
    }

    // Minor 변환
    MinorResponse toMinorResponse(Minor minor);
    Minor toMinorEntity(CreateMinorRequest request);
    default MinorResponses toMinorResponses(List<Minor> minors) {
        List<MinorResponse> minorResponses = minors.stream()
                .map(this::toMinorResponse)
                .collect(Collectors.toList());
        return MinorResponses.builder().minors(minorResponses).build();
    }
}
