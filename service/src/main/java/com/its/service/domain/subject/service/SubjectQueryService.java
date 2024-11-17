package com.its.service.domain.subject.service;

import com.its.service.common.error.code.SubjectErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.subject.dto.response.SubjectResponses;
import com.its.service.domain.subject.entity.Subject;
import com.its.service.domain.subject.mapper.SubjectMapper;
import com.its.service.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectQueryService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectResponses getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjectMapper.toSubjectResponses(subjects);
    }

    public SubjectResponses getCurrentYearSubject() {
        int currentYear = LocalDate.now().getYear();
        List<Subject> byExamYear = subjectRepository.findByExamYear(currentYear);
        return subjectMapper.toSubjectResponses(byExamYear);
    }

    public Subject findSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));
    }

}
