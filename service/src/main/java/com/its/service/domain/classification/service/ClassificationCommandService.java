package com.its.service.domain.classification.service;

import com.its.service.common.error.code.MajorErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.dto.request.CreateMajorRequest;
import com.its.service.domain.classification.dto.request.CreateMinorRequest;
import com.its.service.domain.classification.dto.response.MajorResponse;
import com.its.service.domain.classification.dto.response.MinorResponse;
import com.its.service.domain.classification.entity.Major;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.mapper.ClassificationMapper;
import com.its.service.domain.classification.repository.MajorRepository;
import com.its.service.domain.classification.repository.MinorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassificationCommandService {

    private final MajorRepository majorRepository;
    private final MinorRepository minorRepository;
    private final ClassificationMapper classificationMapper;


    //    대과목 생성
    public MajorResponse createMajor(CreateMajorRequest request) {
        Major major = classificationMapper.toMajorEntity(request);
        Major savedMajor = majorRepository.save(major);

        return classificationMapper.toMajorResponse(savedMajor);
    }
    //    소과목 생성
    public MinorResponse createMinor(CreateMinorRequest request) {
        Major foundMajor = majorRepository.findById(request.majorId()).orElseThrow(() -> new CustomException(MajorErrorCode.MAJOR_NOT_FOUND));
        Minor minor = classificationMapper.toMinorEntity(request, foundMajor);
        Minor savedMinor = minorRepository.save(minor);

        return classificationMapper.toMinorResponse(savedMinor);
    }
}
