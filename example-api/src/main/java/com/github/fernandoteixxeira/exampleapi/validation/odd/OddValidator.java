package com.github.fernandoteixxeira.exampleapi.validation.odd;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class OddValidator implements ConstraintValidator<Odd, Integer> {

    private Odd annotation;

    @Override
    public void initialize(final Odd annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final Integer number, final ConstraintValidatorContext context) {
        if (Objects.nonNull(number) && number % 2 == 1) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(annotation.message() + " - " + number).addConstraintViolation();
        return false;
    }

}

