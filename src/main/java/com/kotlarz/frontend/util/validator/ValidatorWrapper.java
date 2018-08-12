package com.kotlarz.frontend.util.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import java.util.function.Function;

public class ValidatorWrapper {
    public static <T> AbstractValidator<T> create(String message, Function<T, Boolean> conditionHandler) {
        return new AbstractValidator<T>(message) {
            @Override
            public ValidationResult apply(T value, ValueContext context) {
                return toResult(value, conditionHandler.apply(value));
            }
        };
    }
}
