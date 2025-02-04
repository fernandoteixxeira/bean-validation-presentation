package com.github.fernandoteixxeira.exampleapi.validation.period;

import com.github.fernandoteixxeira.exampleapi.controller.LifeOfTime;
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
public class ValidPeriodOfLifeValidator implements ConstraintValidator<ValidPeriod, LifeOfTime> {
    PrinterService printerService;

    @Override
    public boolean isValid(final LifeOfTime value, final ConstraintValidatorContext context) {
        printerService.execute();
        if (Objects.isNull(value) || Objects.isNull(value.birthDate()) || Objects.isNull(value.deathDate())) {
            return false;
        }
        return value.birthDate().isBefore(value.deathDate());
    }

}
