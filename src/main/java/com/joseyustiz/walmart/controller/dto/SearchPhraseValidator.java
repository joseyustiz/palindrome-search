package com.joseyustiz.walmart.controller.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SearchPhraseValidator implements ConstraintValidator<ValidSearchPhrase, String> {
    @Override
    public void initialize(ValidSearchPhrase constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phrase, ConstraintValidatorContext constraintValidatorContext) {
        if(phrase == null)
            return false;

        boolean valid;
        String value = phrase.trim();
        try {
            Long.parseLong(value);
            valid= true;
        }catch (NumberFormatException e){
            valid = value.length() > 2;
        }
        return valid;
    }
}
