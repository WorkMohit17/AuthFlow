package com.week6.AuthFlow.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "{jakarta.validation.constraints.StrongPassword.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
