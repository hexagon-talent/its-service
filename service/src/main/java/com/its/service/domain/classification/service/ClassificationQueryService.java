package com.its.service.domain.classification.service;

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
}
