package com.es.core.util.validators.registration.validation;

import com.es.core.form.SignInUserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegistrationFormValidator implements ConstraintValidator<ValidRegistrationUserForm, SignInUserForm> {

    @Override
    public void initialize(ValidRegistrationUserForm validRegistrationUserForm) {

    }

    @Override
    public boolean isValid(SignInUserForm signInUserForm, ConstraintValidatorContext constraintValidatorContext) {
        boolean flag = true;
        String name = signInUserForm.getUsername();
        String pswd = signInUserForm.getPassword();
        String confirmingPswd = signInUserForm.getPasswordConfirm();
        if (name == null || name.toLowerCase().matches("[0-9]+") || name.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Available format - only letters : ")
                    .addNode("username")
                    .addConstraintViolation();
            flag = false;
        }
        if (pswd == null || confirmingPswd != null && !pswd.equals(confirmingPswd) || pswd.isEmpty() || confirmingPswd.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Not equals password and confirming password")
                    .addNode("password")
                    .addConstraintViolation();
            flag = false;
        }
        return flag;
    }
}
