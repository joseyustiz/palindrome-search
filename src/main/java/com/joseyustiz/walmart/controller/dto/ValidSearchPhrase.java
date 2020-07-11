package com.joseyustiz.walmart.controller.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SearchPhraseValidator.class)
@Documented
public @interface ValidSearchPhrase {
    String message() default "invalid phrase";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
