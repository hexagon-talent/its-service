package com.its.service.domain.question.service.question;

import com.its.service.common.error.code.MinorErrorCode;
import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.code.SubjectErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.repository.MinorRepository;
import com.its.service.domain.question.dto.request.CreateQuestionBulkRequest;
import com.its.service.domain.question.dto.request.CreateQuestionRequest;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.dto.response.QuestionResponses;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.QuestionMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import com.its.service.domain.subject.entity.Subject;
import com.its.service.domain.subject.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionCommandService {
    private final QuestionRepository questionRepository;
    private final MinorRepository minorRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionMapper questionMapper;

    /*
    * 단일 문제 등록
    * */
    @Transactional
    public QuestionResponse createSingleQuestion(CreateQuestionRequest request) {
        Question question = questionMapper.toEntity(request);
        Long minorId = question.getMinorId();
        Minor minor = minorRepository.findById(minorId)
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));

        Long subjectId = question.getSubjectId();
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new CustomException(SubjectErrorCode.SUBJECT_NOT_FOUND));

        String questionNumber = question.getQuestionNumber();

        if (questionRepository.existsQuestionByMinorIdAndSubjectIdAndQuestionNumber(minorId, subjectId, questionNumber)) {
            throw new CustomException(QuestionErrorCode.QUESTION_ALREADY_EXISTS);
        }

        Question savedQuestion = questionRepository.save(question);

        return questionMapper.toQuestionResponse(savedQuestion, minor, subject);
    }
    /*
     * 대량 문제 등록
     * */
    @Transactional
    public QuestionResponses createBulkQuestions(CreateQuestionBulkRequest request) {
        List<QuestionResponse> questionResponses = new ArrayList<>();

        for (CreateQuestionRequest questionRequest : request.questions()) {
            QuestionResponse response = createSingleQuestion(questionRequest);
            questionResponses.add(response);
        }

        // QuestionResponses로 변환
        return QuestionResponses.builder().questions(questionResponses).build();
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
