package com.its.service.domain.classification.service;

import com.its.service.common.error.code.MajorErrorCode;
import com.its.service.common.error.code.MinorErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.dto.response.MajorResponses;
import com.its.service.domain.classification.dto.response.MinorResponses;
import com.its.service.domain.classification.entity.Major;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.mapper.ClassificationMapper;
import com.its.service.domain.classification.repository.MajorRepository;
import com.its.service.domain.classification.repository.MinorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassificationQueryService {
    private final ClassificationMapper classificationMapper;
    private final MajorRepository majorRepository;
    private final MinorRepository minorRepository;

    public MajorResponses getAllMajors() {
        List<Major> majors = majorRepository.findAll();
        return classificationMapper.toMajorResponses(majors);
    }
    public MinorResponses getAllMinors() {
        List<Minor> minors = minorRepository.findAll();
        return classificationMapper.toMinorResponses(minors);
    }
    public MinorResponses getAllMinorsByMajorId(Long majorId) {
        List<Minor> minors = minorRepository.findByMajor_MajorId(majorId);
        return classificationMapper.toMinorResponses(minors);
    }

    public Minor findMinorById(Long minorId) {
        return minorRepository.findById(minorId)
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));
    }
    public Major findMajorById(Long majorId) {
        return majorRepository.findById(majorId)
                .orElseThrow(() -> new CustomException(MajorErrorCode.MAJOR_NOT_FOUND));
    }

}
