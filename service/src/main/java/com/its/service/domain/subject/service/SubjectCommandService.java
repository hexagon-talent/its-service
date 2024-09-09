package com.its.service.domain.subject.service;

import com.its.service.domain.subject.dto.response.SubjectResponse;
import com.its.service.domain.subject.dto.request.CreateSubjectRequest;
import com.its.service.domain.subject.entity.Subject;
import com.its.service.domain.subject.mapper.SubjectMapper;
import com.its.service.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectCommandService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    //    과목 생성
    public SubjectResponse createSubject(CreateSubjectRequest request) {
        Subject subject = subjectMapper.toSubjectEntity(request);
        Subject savedSubject = subjectRepository.save(subject);

        return subjectMapper.toSubjectResponse(savedSubject);
    }
}
