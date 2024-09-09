package com.its.service.domain.classification.mapper;

import com.its.service.domain.classification.dto.request.CreateMajorRequest;
import com.its.service.domain.classification.dto.request.CreateMinorRequest;
import com.its.service.domain.classification.dto.response.*;
import com.its.service.domain.classification.entity.Major;
import com.its.service.domain.classification.entity.Minor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassificationMapper {

    // Major 변환 (Entity -> Response)
    @Mapping(target = "majorId", source = "major.majorId")
    @Mapping(target = "majorName", source = "major.majorName")
    MajorResponse toMajorResponse(Major major);

    // Major 생성 시 (DTO -> Entity)
    Major toMajorEntity(CreateMajorRequest request);

    default MajorResponses toMajorResponses(List<Major> majors) {
        List<MajorResponse> majorResponses = majors.stream()
                .map(this::toMajorResponse)
                .toList();
        return MajorResponses.builder().majors(majorResponses).build();
    }

    // Minor 변환 (Entity -> Response)
    @Mapping(target = "minorId", source = "minor.minorId")
    @Mapping(target = "minorName", source = "minor.minorName")
    @Mapping(target = "majorId", source = "minor.major.majorId")
    MinorResponse toMinorResponse(Minor minor);

    // Minor 생성 시 (DTO -> Entity)
    default Minor toMinorEntity(CreateMinorRequest request, Major major) {
        Minor minor = new Minor();
        minor.setMinorName(request.minorName());
        minor.setMajor(major);
        return minor;
    }

    default MinorResponses toMinorResponses(List<Minor> minors) {
        List<MinorResponse> minorResponses = minors.stream()
                .map(this::toMinorResponse)
                .toList();
        return MinorResponses.builder().minors(minorResponses).build();
    }
}
