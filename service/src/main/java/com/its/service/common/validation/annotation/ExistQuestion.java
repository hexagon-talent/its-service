package com.its.service.common.validation.annotation;

import com.its.service.common.validation.validator.ExistQuestionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented // 애노테이션이 문서화될 수 있음을 나타내기
@Constraint(validatedBy = ExistQuestionValidator.class) //이 애노테이션이 사용할 검증기를 지정하기
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER}) //애노테이션을 어디에 적용할 수 있는지를 정의하기
@Retention(RetentionPolicy.RUNTIME) //애노테이션 정보가 런타임에 유지됨을 나타내기
public @interface ExistQuestion {
    String message() default "해딩 문제가 존재하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
