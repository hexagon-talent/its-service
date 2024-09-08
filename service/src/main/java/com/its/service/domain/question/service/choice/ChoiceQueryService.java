package com.its.service.domain.question.service.choice;

import com.its.service.common.error.code.QuestionErrorCode;
import com.its.service.common.error.exception.CustomException;
import com.its.service.domain.question.dto.response.ChoicesResponse;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.ChoiceMapper;
import com.its.service.domain.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChoiceQueryService {
    private final QuestionRepository questionRepository;
    private final ChoiceMapper choiceMapper;

    @Transactional
    public ChoicesResponse getChoicesByQuestionId(String questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
        return choiceMapper.toChoicesResponse(question);
    }
}
