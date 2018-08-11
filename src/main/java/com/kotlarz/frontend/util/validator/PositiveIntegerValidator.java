package com.kotlarz.frontend.util.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

public class PositiveIntegerValidator extends AbstractValidator<String> {
    private static String MESSAGE = "Value has to be positive number";

    public PositiveIntegerValidator() {
        super(MESSAGE);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        boolean ok;

        try {
            long parsed = Long.parseLong(value);
            ok = parsed > 0;
        } catch (Exception ex) {
            ok = false;
        }

        return toResult(value, ok);
    }
}
