package com.its.service.domain.question.service.explanation;

import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.question.dto.response.ExplanationResponse;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.ExplanationMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExplanationQueryService {
    private final ExplanationMapper explanationMapper;
    private final QuestionRepository questionRepository;
    /*
     * 특정 문제의 해설 조회
     * */
    public ExplanationResponse getExplanationByQuestionId(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));

        return explanationMapper.toExplanationResponse(question);
    }

}
