package com.challenge.itausegdev.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryConstraint {
    String message() default "Invalid Insurance Category";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
