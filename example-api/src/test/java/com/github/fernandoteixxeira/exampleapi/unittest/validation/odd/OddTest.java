package com.github.fernandoteixxeira.exampleapi.unittest.validation.odd;

import com.github.fernandoteixxeira.exampleapi.validation.odd.Odd;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.Function;

public class OddTest {
    record Number(@Odd Integer number) {
    }

    @Test
    void given_a_odd_number_when_validating_the_return_no_violations() {
        val number = new Number(1);
        val violations = validate(validator -> validator.validate(number));
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void given_a_pair_number_when_validating_the_return_any_violations() {
        val number = new Number(2);
        val violations = validate(validator -> validator.validate(number));
        Assertions.assertThat(violations)
                .hasSize(1)
                .element(0)
                .extracting(ConstraintViolation::getMessage)
                .isEqualTo("Invalid odd number - " + number.number);
    }

    private static <T> Set<ConstraintViolation<T>> validate(Function<Validator, Set<ConstraintViolation<T>>> function) {
        try (val validatorFactory = Validation.buildDefaultValidatorFactory()) {
            val validator = validatorFactory.getValidator();
            return function.apply(validator);
        }
    }
}
