package com.example.proiectps1.validators;

import com.example.proiectps1.model.User.RoleType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RoleTypeSubsetValidator.class)
public @interface RoleTypeSubset {
    RoleType[] anyOf();

    String message() default "must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
