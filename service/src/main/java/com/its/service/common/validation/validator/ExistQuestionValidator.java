package com.its.service.common.validation.validator;

import com.its.service.common.validation.annotation.ExistQuestion;
import com.its.service.domain.question.repository.QuestionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistQuestionValidator implements ConstraintValidator<ExistQuestion, String> {
    private final QuestionRepository questionRepository;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return questionRepository.existsById(value);
    }
}
