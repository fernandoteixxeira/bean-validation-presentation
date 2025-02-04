package com.github.fernandoteixxeira.exampleapi.validation.period;

import com.github.fernandoteixxeira.exampleapi.controller.Period;
import com.github.fernandoteixxeira.exampleapi.service.PrinterService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ValidPeriodValidator implements ConstraintValidator<ValidPeriod, Period> {

    PrinterService printerService;

    @Override
    public void initialize(final ValidPeriod constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final Period value, final ConstraintValidatorContext context) {
        printerService.execute();
        if (Objects.isNull(value) || Objects.isNull(value.initialDate()) || Objects.isNull(value.finalDate())) {
            return false;
        }
        return value.initialDate().isBefore(value.finalDate());
    }

}
