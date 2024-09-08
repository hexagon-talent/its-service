package com.its.service.domain.question.service.question;

import com.its.service.common.error.code.MinorErrorCode;
import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.classification.entity.Minor;
import com.its.service.domain.classification.repository.MinorRepository;
import com.its.service.domain.question.dto.response.QuestionResponse;
import com.its.service.domain.question.dto.response.QuestionResponses;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.QuestionMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionQueryService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final MinorRepository minorRepository;
    /*
    * 특정 문제 조회
    * */
    public QuestionResponse getQuestionById(String questionId) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));

        // minorId를 이용하여 Minor, Major, Subject 데이터를 조회
        Minor minor = minorRepository.findById(question.getMinorId())
                .orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));

        return questionMapper.toQuestionResponse(question, minor);

    }

    /*
    * 소과목에 속하는 문제들을 조회
    * */
    public QuestionResponses getQuestionsByMinorId(Long minorId) {
        Minor minor = minorRepository.findById(minorId).orElseThrow(() -> new CustomException(MinorErrorCode.MINOR_NOT_FOUND));

        List<Question> questions = questionRepository.findByMinorId(minorId);

        return questionMapper.toQuestionResponses(questions, minor);
    }

}
