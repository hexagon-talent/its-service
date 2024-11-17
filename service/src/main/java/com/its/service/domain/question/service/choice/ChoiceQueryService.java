package com.its.service.domain.question.service.choice;

import com.its.service.domain.question.dto.response.ChoicesResponse;
import com.its.service.domain.question.entity.Question;
import com.its.service.domain.question.mapper.ChoiceMapper;
import com.its.service.domain.question.service.question.QuestionQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChoiceQueryService {
    private final ChoiceMapper choiceMapper;
    private final QuestionQueryService questionQueryService;


    @Transactional
    public ChoicesResponse getChoicesByQuestionId(String questionId) {
        Question question = questionQueryService.findQuestionById(questionId);
        return choiceMapper.toChoicesResponse(question);
    }

}
