package com.github.fernandoteixxeira.exampleapi.validation.odd;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.hibernate.validator.internal.engine.constraintvalidation.CrossParameterConstraintValidatorContextImpl;

import java.util.HashMap;
import java.util.Map;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class OddMethodValidator implements ConstraintValidator<Odd, Object[]> {

    private Odd annotation;

    @Override
    public void initialize(final Odd annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(final Object[] value, final ConstraintValidatorContext context) {
        var parameterByName = generateMethodParameterByName(value, context);
        var number1 = (Integer) parameterByName.get(annotation.field1());
        var number2 = (Integer) parameterByName.get(annotation.field2());
        return number1 % 2 == 1 &&  number2 % 2 == 1;
    }

    private Map<String, Object> generateMethodParameterByName(final Object[] methodParameters,
                                                              final ConstraintValidatorContext constraintValidatorContext) {
        var constraintValidatorContextImpl = (CrossParameterConstraintValidatorContextImpl) constraintValidatorContext;
        var methodParametersName = constraintValidatorContextImpl.getMethodParameterNames();
        var methodParameterByName = new HashMap<String, Object>();
        for (int i = 0; i < methodParameters.length; i++) {
            methodParameterByName.put(methodParametersName.get(i), methodParameters[i]);
        }
        return methodParameterByName;
    }

}