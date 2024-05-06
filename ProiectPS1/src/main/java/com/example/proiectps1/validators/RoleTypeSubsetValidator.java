package com.example.proiectps1.validators;

import com.example.proiectps1.model.User.RoleType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RoleTypeSubsetValidator implements ConstraintValidator<RoleTypeSubset, RoleType> {

    private RoleType[] subset;

    @Override
    public void initialize(RoleTypeSubset constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(RoleType roleType, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.asList(subset).contains(roleType);
    }
}
