package com.challenge.itausegdev.validator;

import com.challenge.itausegdev.model.CategoryTypes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CategoryValidator implements ConstraintValidator<CategoryConstraint, String> {

    @Override
    public void initialize(CategoryConstraint categoryConstraint) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return !Arrays.stream(CategoryTypes.values()).filter(it -> it.name().equals(value)).toList().isEmpty();
    }
}
