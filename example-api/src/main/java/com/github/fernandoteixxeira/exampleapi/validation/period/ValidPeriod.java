package com.github.fernandoteixxeira.exampleapi.validation.period;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidPeriodValidator.class, ValidPeriodOfLifeValidator.class})
public @interface ValidPeriod {
    String message() default "Invalid Period";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
