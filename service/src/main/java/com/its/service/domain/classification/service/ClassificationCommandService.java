package com.its.service.domain.classification.service;

import com.its.service.common.error.code.MajorErrorCode;
import com.its.service.common.error.code.SubjectErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.dto.request.CreateMajorRequest;
import com.its.service.domain.classification.dto.request.CreateMinorRequest;
import com.its.service.domain.classification.dto.request.CreateSubjectRequest;
import com.its.service.domain.classification.dto.response.MajorResponse;
import com.its.service.domain.classification.dto.response.MinorResponse;
import com.its.service.domain.classification.dto.response.SubjectResponse;
import com.its.service.domain.classification.entity.Major;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.entity.Subject;
import com.its.service.domain.classification.mapper.ClassificationMapper;
import com.its.service.domain.classification.repository.MajorRepository;
import com.its.service.domain.classification.repository.MinorRepository;
import com.its.service.domain.classification.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassificationCommandService {
    private final SubjectRepository subjectRepository;
    private final MajorRepository majorRepository;
    private final MinorRepository minorRepository;
    private final ClassificationMapper classificationMapper;

    //    과목 생성
    public SubjectResponse createSubject(CreateSubjectRequest request) {
        Subject subject = classificationMapper.toSubjectEntity(request);
        Subject savedSubject = subjectRepository.save(subject);

        return classificationMapper.toSubjectResponse(savedSubject);
    }
    //    대과목 생성
    public MajorResponse createMajor(CreateMajorRequest request) {
        subjectRepository.findById(request.subjectId()).orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));
        Major major = classificationMapper.toMajorEntity(request);
        Major savedMajor = majorRepository.save(major);

        return classificationMapper.toMajorResponse(savedMajor);
    }
    //    소과목 생성
    public MinorResponse createMinor(CreateMinorRequest request) {
        majorRepository.findById(request.majorId()).orElseThrow(() -> new CustomException(MajorErrorCode.MAJOR_NOT_FOUND));
        Minor minor = classificationMapper.toMinorEntity(request);
        Minor savedMinor = minorRepository.save(minor);

        return classificationMapper.toMinorResponse(savedMinor);
    }
}
