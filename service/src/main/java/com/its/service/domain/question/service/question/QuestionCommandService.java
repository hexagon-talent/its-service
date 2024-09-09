package com.its.service.domain.question.service.question;

import com.its.service.common.error.code.MinorErrorCode;
import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.code.SubjectErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.repository.MinorRepository;
import com.its.service.domain.question.dto.request.CreateQuestionRequest;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.QuestionMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import com.its.service.domain.subject.entity.Subject;
import com.its.service.domain.subject.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionCommandService {
    private final QuestionRepository questionRepository;
    private final MinorRepository minorRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionMapper questionMapper;

    /*
    * 문제 등록
    * */
    @Transactional
    public QuestionResponse createQuestion(CreateQuestionRequest request) {
        Question question = questionMapper.toEntity(request);
        Question savedQuestion = questionRepository.save(question);

        Minor minor = minorRepository.findById(savedQuestion.getMinorId())
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));

        Subject subject = subjectRepository.findById(question.getSubjectId())
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));

        return questionMapper.toQuestionResponse(savedQuestion, minor, subject);
    }

    /*
     * 문제 삭제
     * */
    @Transactional
    public void deleteQuestionById(String questionId) {
        // 문제 존재 여부 확인
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
        // 문제 삭제 로직 수행
        questionRepository.delete(question);
    }
}
