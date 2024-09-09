package com.its.service.domain.question.service.explanation;

import com.its.service.common.error.code.ExplanationErrorCode;
import com.its.service.common.error.code.MinorErrorCode;
import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.code.SubjectErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.repository.MinorRepository;
import com.its.service.domain.question.dto.request.CreateExplanationRequest;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.entity.Explanation;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.ExplanationMapper;
import com.its.service.domain.question.mapper.QuestionMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import com.its.service.domain.subject.entity.Subject;
import com.its.service.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExplanationCommandService {
    private final QuestionRepository questionRepository;
    private final MinorRepository minorRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionMapper questionMapper;
    private final ExplanationMapper explanationMapper;

    @Transactional
    public QuestionResponse createExplanation(String questionId, CreateExplanationRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
        Minor minor = minorRepository.findById(question.getMinorId())
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));
        Subject subject = subjectRepository.findById(question.getSubjectId())
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));

        // 해설이 이미 존재하는지 확인
        if (question.getExplanation() != null) {
            throw new CustomException(ExplanationErrorCode.EXPLANATION_ALREADY_EXISTS);
        }

        Explanation addedExplanation = explanationMapper.toEntity(request);
        question.setExplanation(addedExplanation);
        questionRepository.save(question);

        return questionMapper.toQuestionResponse(question, minor, subject);
    }

    @Transactional
    public QuestionResponse deleteExplanation(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
        Minor minor = minorRepository.findById(question.getMinorId())
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));
        Subject subject = subjectRepository.findById(question.getSubjectId())
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));

        question.setExplanation(null);  // 해설 삭제
        questionRepository.save(question);

        return questionMapper.toQuestionResponse(question, minor, subject);
    }

    @Transactional
    public QuestionResponse updateExplanation(String questionId, CreateExplanationRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
        Minor minor = minorRepository.findById(question.getMinorId())
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));
        Subject subject = subjectRepository.findById(question.getSubjectId())
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));
        Explanation updatedExplanation = explanationMapper.toEntity(request);
        question.setExplanation(updatedExplanation);
        questionRepository.save(question);

        return questionMapper.toQuestionResponse(question,minor, subject);
    }
}
