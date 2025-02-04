package com.github.fernandoteixxeira.exampleapi.unittest.validation.period;

import com.github.fernandoteixxeira.exampleapi.controller.Period;
import com.github.fernandoteixxeira.exampleapi.service.PrinterService;
import com.github.fernandoteixxeira.exampleapi.validation.period.ValidPeriod;
import com.github.fernandoteixxeira.exampleapi.validation.period.ValidPeriodValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class ValidPeriodTest {
    record Filter(@ValidPeriod Period percreateCustomiod) {
    }

    Map<Class<?>, Supplier<? extends ConstraintValidator<?, ?>>> creatorByClass = Map.of(
            ValidPeriodValidator.class, () -> new ValidPeriodValidator(new PrinterService())
    );

    @Test
    void given_a_valid_period_when_validating_the_return_no_violations() {
        val initialDate = LocalDate.now();
        val finalDate = LocalDate.now().plusDays(1);
        val period = new Period(initialDate, finalDate);
        val filter = new Filter(period);

        val violations = validate(validator -> validator.validate(filter), creatorByClass);

        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void given_a_invalid_period_when_validating_the_return_any_violations() {
        val initialDate = LocalDate.now().plusDays(1);
        val finalDate = LocalDate.now();
        val period = new Period(initialDate, finalDate);
        val filter = new Filter(period);

        val violations = validate(validator -> validator.validate(filter), creatorByClass);

        Assertions.assertThat(violations)
                .hasSize(1)
                .element(0)
                .extracting(ConstraintViolation::getMessage)
                .isEqualTo("Invalid Period");
    }

    private static <T> Set<ConstraintViolation<T>> validate(Function<Validator, Set<ConstraintViolation<T>>> function, Map<Class<?>, Supplier<? extends ConstraintValidator<?, ?>>> creatorByClass) {
        val constraintValidatorFactory = createCustomValidatorFactory(creatorByClass);
        try (val validatorFactory = Validation.byDefaultProvider()
                .configure()
                .constraintValidatorFactory(constraintValidatorFactory)
                .buildValidatorFactory()) {
            val validator = validatorFactory.getValidator();
            return function.apply(validator);
        }
    }

    private static ConstraintValidatorFactory createCustomValidatorFactory(Map<Class<?>, Supplier<? extends ConstraintValidator<?, ?>>> creatorByClass) {
        return new ConstraintValidatorFactory() {
            @Override
            public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
                if (creatorByClass.containsKey(key)) {
                    return (T) creatorByClass.get(key).get();
                }
                try {
                    return key.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Could not instantiate validator: " + key, e);
                }
            }

            @Override
            public void releaseInstance(ConstraintValidator<?, ?> instance) {
            }
        };
    }
}
