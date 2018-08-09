package com.kotlarz.frontend.util.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;

public class NotEmptyStringValidator extends AbstractValidator<String> {
    private static String MESSAGE = "Field cannot be empty";

    public NotEmptyStringValidator() {
        super(MESSAGE);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        return toResult(MESSAGE, StringUtils.isNotEmpty(value));
    }
}
